package com.example.deliverymanagement.service;

import com.example.deliverymanagement.dto.request.OrderRequestDto;
import com.example.deliverymanagement.dto.response.OrderResponseDto;
import com.example.deliverymanagement.dto.response.ResponseDto;
import com.example.deliverymanagement.entity.Cart;
import com.example.deliverymanagement.entity.Customer;

import java.util.List;

public interface OrderService {
    ResponseDto save(Cart cart, Customer customer, OrderRequestDto orderRequestDto);
    ResponseDto delete(Long id);
    OrderResponseDto getById(Long id);
    List<OrderResponseDto> getAll();
}
