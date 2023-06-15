package com.example.deliverymanagement.service.impl;

import com.example.deliverymanagement.dto.request.CustomerRequestDto;
import com.example.deliverymanagement.dto.response.CustomerResponseDto;
import com.example.deliverymanagement.dto.response.ResponseDto;
import com.example.deliverymanagement.entity.ConfirmationToken;
import com.example.deliverymanagement.entity.Customer;
import com.example.deliverymanagement.exceptions.CustomerNotFoundException;
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
import java.util.stream.Collectors;

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
        if (save!=null){
            return new ResponseDto("Save is successfull");
        }else {
            throw new CustomerNotFoundException();
        }
    }

    @Override
    public ResponseDto delete(Long id) {
        Customer customer=customerRepository.getCustomerById(id);
        if (customer!=null){
            customerRepository.delete(customer);
            return new ResponseDto("Deleted is successfully!");
        }else {
            throw new CustomerNotFoundException();
        }
    }

    @Override
    public List<CustomerResponseDto> getAll() {
        return customerRepository.findAll().stream()
                .map(customer -> modelMapper.map(customer,CustomerResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseDto put(Customer customer) {
        if (customerRepository.save(customer)!=null){
            return new ResponseDto("Successfull!");
        }else {
            throw new CustomerNotFoundException();
        }
    }

    @Override
    public Customer findByEmail(String email) {
        return customerRepository.findCustomerByEmail(email);
    }

    @Override
    public CustomerResponseDto findById(Long id) {
        return modelMapper.map(customerRepository.findById(id),CustomerResponseDto.class);
    }
}
