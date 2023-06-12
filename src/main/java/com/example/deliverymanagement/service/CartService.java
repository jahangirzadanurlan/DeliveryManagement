package com.example.deliverymanagement.service;

import com.example.deliverymanagement.dto.response.ResponseDto;
import com.example.deliverymanagement.entity.Cart;

public interface CartService {
    ResponseDto save(Cart cart);
}
