package com.example.deliverymanagement.controller;

import com.example.deliverymanagement.dto.request.CustomerRequestDto;
import com.example.deliverymanagement.dto.response.CustomerResponseDto;
import com.example.deliverymanagement.dto.response.ResponseDto;
import com.example.deliverymanagement.entity.ConfirmationToken;
import com.example.deliverymanagement.entity.Customer;
import com.example.deliverymanagement.entity.ForgetPasswordToken;
import com.example.deliverymanagement.repository.CustomerRepository;
import com.example.deliverymanagement.service.ConfirmationTokenService;
import com.example.deliverymanagement.service.CustomerService;
import com.example.deliverymanagement.service.EmailSenderService;
import com.example.deliverymanagement.service.ForgetPasswordTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final EmailSenderService emailSenderService;
    private final ConfirmationTokenService confirmationTokenService;
    private final ForgetPasswordTokenService forgetPasswordTokenService;
    private final CustomerRepository customerRepository;

    @GetMapping
    public List<CustomerResponseDto> user(){
        return customerService.getAll();
    }

    @PostMapping
    public ResponseDto saveCustomer(@RequestBody CustomerRequestDto customerRequestDto){
        return customerService.save(customerRequestDto);
    }
    @GetMapping("/{uuid}")
    public ResponseDto confirmCustomer(@PathVariable UUID uuid){
        ConfirmationToken token=confirmationTokenService.getTokenByUUID(uuid.toString());
        if (token!=null){
            token.setConfirmedAt(Date.valueOf(LocalDate.now()));
            confirmationTokenService.save(token);
            Customer customer=token.getCustomer();
            System.out.println(customer);
            customer.setIsEnabled(true);
            customerService.put(customer);
            return new ResponseDto("Your account has been activated");
        }else {
            return new ResponseDto("Link is wrong!!!");
        }
    }
    //Sifre yenilenmesi ucun mail gonderilir
    @PostMapping("/password-resets")
    public ResponseDto resetPasswordMail(@RequestBody Map<String, String> requestBody){
        String email = requestBody.get("email");

        Customer customer=customerService.findByEmail(email);
        if (customer!=null){
            ForgetPasswordToken token=ForgetPasswordToken.builder()
                    .token(UUID.randomUUID().toString())
                    .createdAt(Date.valueOf(LocalDate.now()))
                    .customer(customer)
                    .build();
            String text="/customers/password/";
            forgetPasswordTokenService.save(token);
            emailSenderService.resetMail(token,email,text);
            return new ResponseDto("Password reset email sended");
        }else {
            return new ResponseDto("Email not Found!!!");
        }
    }

    @GetMapping("/password-reset/{uuid}")
    public ResponseDto confirmLink(@PathVariable UUID uuid){
        ForgetPasswordToken token=forgetPasswordTokenService.getTokenByUUID(uuid.toString());
        if (token!=null){
            token.setConfirmedAt(Date.valueOf(LocalDate.now()));
            forgetPasswordTokenService.save(token);//**
            Customer customer=customerRepository.findCustomerByEmail(token.getCustomer().getEmail());
            customer.setResetEnabled(true);
            customerService.put(customer);
            return new ResponseDto("You can set a new password by sending a put request to 'localhost:8080/customers/password' link.");
        }else {
            return new ResponseDto("Link is Wrong!!!");
        }
    }
    @PutMapping("/password")
    public ResponseDto resetPassword(@RequestBody Map<String, String> requestBody ){
        String email = requestBody.get("email");
        String password = requestBody.get("password");

        Customer customer=customerRepository.findCustomerByEmail(email);
        if (customer!=null && customer.getResetEnabled().equals(true) && customer.getIsEnabled().equals(true)){
            customer.setPassword(password);
            customer.setResetEnabled(false);//Yeniden istek gonderib deyise bilmesin deye false edirem
            customerService.put(customer);
            return new ResponseDto("Password changed successfully!");
        }else {
            return new ResponseDto("Customer not found!!!");
        }
    }

}
