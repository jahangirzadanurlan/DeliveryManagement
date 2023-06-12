package com.example.deliverymanagement.service;

import com.example.deliverymanagement.dto.request.FoodRequestDto;
import com.example.deliverymanagement.dto.response.FoodResponseDto;
import com.example.deliverymanagement.dto.response.ResponseDto;

import java.util.List;

public interface FoodService {
    ResponseDto save(FoodRequestDto foodRequestDto);
    ResponseDto put(Long id,FoodRequestDto foodRequestDto);
    ResponseDto delete(Long id);
    FoodResponseDto getById(Long id);
    List<FoodResponseDto> foods();
}
