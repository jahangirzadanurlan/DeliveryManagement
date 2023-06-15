package com.example.deliverymanagement.service;

import com.example.deliverymanagement.dto.request.CategoryRequestDto;
import com.example.deliverymanagement.dto.response.CategoryResponseDto;
import com.example.deliverymanagement.dto.response.ResponseDto;

import java.util.List;

public interface CategoryService {
    ResponseDto save(CategoryRequestDto categoryRequestDto);
    ResponseDto put(Long id, CategoryRequestDto categoryRequestDto);
    ResponseDto delete(Long id);
    CategoryResponseDto getById(Long id);
    List<CategoryResponseDto> getAll();
}
