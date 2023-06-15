package com.example.deliverymanagement.exceptions;

import com.example.deliverymanagement.enums.ExceptionEnum;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(){
        super(ExceptionEnum.CUSTOMER_NOT_FOUND_EXCEPTION.getMessage());
    }
}
