package com.example.deliverymanagement.service;

import com.example.deliverymanagement.dto.request.CustomerRequestDto;
import com.example.deliverymanagement.dto.response.CustomerResponseDto;
import com.example.deliverymanagement.dto.response.ResponseDto;
import com.example.deliverymanagement.entity.Customer;

import java.util.List;

public interface CustomerService {
    ResponseDto save(CustomerRequestDto customerRequestDto);
    List<CustomerResponseDto> getAll();
    ResponseDto put(Customer customer);
    Customer findByEmail(String email);

}
