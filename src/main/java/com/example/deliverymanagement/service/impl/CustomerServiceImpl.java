package com.example.deliverymanagement.service.impl;

import com.example.deliverymanagement.dto.request.CustomerRequestDto;
import com.example.deliverymanagement.dto.response.CustomerResponseDto;
import com.example.deliverymanagement.dto.response.ResponseDto;
import com.example.deliverymanagement.entity.ConfirmationToken;
import com.example.deliverymanagement.entity.Customer;
import com.example.deliverymanagement.repository.CustomerRepository;
import com.example.deliverymanagement.service.ConfirmationTokenService;
import com.example.deliverymanagement.service.CustomerService;
import com.example.deliverymanagement.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final EmailSenderService emailSenderService;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public ResponseDto save(CustomerRequestDto customerRequestDto) {
        Customer save = customerRepository.save(modelMapper.map(customerRequestDto, Customer.class));
        Customer customer=customerRepository.findCustomerByEmail(customerRequestDto.getEmail());
        ConfirmationToken token=ConfirmationToken.builder()
                .token(UUID.randomUUID().toString())
                .createdAt(Date.valueOf(LocalDate.now()))
                .customer(customer)
                .build();
        String text="/customers/";
        confirmationTokenService.save(token);
        emailSenderService.sendMail(customerRequestDto,token,text);
        return save!=null? new ResponseDto("Confirm your account with the email we sent you!"):
                new ResponseDto("Save is unsuccessfull!");
    }

    @Override
    public List<CustomerResponseDto> getAll() {
        return null;
    }

    @Override
    public ResponseDto put(Customer customer) {
        return customerRepository.save(customer)!=null?
                new ResponseDto("Successfull!"):
                new ResponseDto("unsuccessfull!!!");
    }

    @Override
    public Customer findByEmail(String email) {
        return customerRepository.findCustomerByEmail(email);
    }
}
