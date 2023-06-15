package com.example.deliverymanagement.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionEnum {
    CUSTOMER_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND,"Customer not found!"),
    CART_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND,"Cart not found!"),
    FOOD_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND,"Food not found"),
    DRIVER_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND,"Driver not found"),

    ILLEGAL_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND,"Illegal Argument not found!");


    private final HttpStatus status;
    private final String message;
}
