package com.example.deliverymanagement.exceptions;

import com.example.deliverymanagement.enums.ExceptionEnum;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(){
        super(ExceptionEnum.CART_NOT_FOUND_EXCEPTION.getMessage());
    }
}
