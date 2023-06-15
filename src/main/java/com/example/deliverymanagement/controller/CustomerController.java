package com.example.deliverymanagement.controller;

import com.example.deliverymanagement.dto.request.CustomerRequestDto;
import com.example.deliverymanagement.dto.request.OrderRequestDto;
import com.example.deliverymanagement.dto.response.CustomerResponseDto;
import com.example.deliverymanagement.dto.response.FoodResponseDto;
import com.example.deliverymanagement.dto.response.ResponseDto;
import com.example.deliverymanagement.entity.*;
import com.example.deliverymanagement.repository.CartRepository;
import com.example.deliverymanagement.repository.CustomerRepository;
import com.example.deliverymanagement.repository.FoodRepository;
import com.example.deliverymanagement.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final ModelMapper modelMapper;
    private final CustomerService customerService;
    private final EmailSenderService emailSenderService;
    private final ConfirmationTokenService confirmationTokenService;
    private final ForgetPasswordTokenService forgetPasswordTokenService;
    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;
    private final FoodService foodService;
    private final CartService cartService;
    private final FoodRepository foodRepository;
    private final OrderService orderService;

    @GetMapping
    public List<CustomerResponseDto> user(){
        return customerService.getAll();
    }

    @PostMapping
    public ResponseDto saveCustomer(@RequestBody CustomerRequestDto customerRequestDto){
        customerService.save(customerRequestDto);
        Customer customer=customerService.findByEmail(customerRequestDto.getEmail());
        Cart cart=Cart.builder()
                .customer(customer)
                .build();
        cartService.save(cart);
        customer.setCart(cart);
        customerService.put(customer);
        return new ResponseDto("Save is successfully!");
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
    @GetMapping("/foods")
    public List<FoodResponseDto> allFoods(){
        return foodService.foods();
    }
    @GetMapping("/foods/{id}")
    public FoodResponseDto getFood(@PathVariable Long id){
        return foodService.getById(id);
    }
    @GetMapping("/{id}/foods/cart")
    public List<FoodResponseDto> allCartFoods(@PathVariable Long id){
        Customer customer=customerRepository.getCustomerById(id);
        Cart cart=cartRepository.findCartByCustomer(customer);
        if (cart!=null){
            return cart.getFoods().stream()
                    .map(food -> modelMapper.map(food,FoodResponseDto.class))
                    .collect(Collectors.toList());
        }else {
            throw new RuntimeException("Cart is not found");
        }

    }

    @PostMapping("/{id}/foods/{food_id}/cart")
    public ResponseDto addFoodToCart(@PathVariable Long id,@PathVariable Long food_id){
        Cart cart = cartRepository.findCartByCustomerId(id);
        Food food = foodRepository.getFoodById(food_id);
        food.setCart(cart);
        foodRepository.save(food);
        if (cart != null) {
            cart.getFoods().add(modelMapper.map(food, Food.class));
            int foodCount = cart.getCount()+1;
            Double totalAmount = cart.getTotalAmount()+food.getAmount();

            cart.setCount(foodCount);
            cart.setTotalAmount(totalAmount);
            cartService.save(cart);
            return new ResponseDto("Adding successful!");
        } else {
            return new ResponseDto("Cart not found!");
        }

    }
    @DeleteMapping("/{id}/cart/{food_id}")
    public ResponseDto deleteFoodInCart(@PathVariable Long id,@PathVariable Long food_id){
        foodService.delete(food_id);
        return new ResponseDto("Food deleting successfull!");
    }

    @PostMapping("/{id}/cart/checkout")
    public void checkout(@PathVariable Long id, @RequestBody OrderRequestDto orderRequestDto){
        Customer customer=customerRepository.getCustomerById(id);
        Cart cart = cartRepository.findCartByCustomerId(id);
        orderService.save(cart,customer,orderRequestDto);
    }



}
