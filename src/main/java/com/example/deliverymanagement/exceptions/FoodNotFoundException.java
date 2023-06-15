package com.example.deliverymanagement.exceptions;

import com.example.deliverymanagement.enums.ExceptionEnum;

public class FoodNotFoundException extends RuntimeException{
    public FoodNotFoundException(){
        super(ExceptionEnum.FOOD_NOT_FOUND_EXCEPTION.getMessage());
    }
}
