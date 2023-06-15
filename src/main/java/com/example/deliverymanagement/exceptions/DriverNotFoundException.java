package com.example.deliverymanagement.exceptions;

import com.example.deliverymanagement.enums.ExceptionEnum;

public class DriverNotFoundException extends RuntimeException {
    public DriverNotFoundException(){
        super(ExceptionEnum.DRIVER_NOT_FOUND_EXCEPTION.getMessage());
    }
}
