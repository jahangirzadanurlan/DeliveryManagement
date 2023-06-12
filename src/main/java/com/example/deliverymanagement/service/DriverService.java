package com.example.deliverymanagement.service;

import com.example.deliverymanagement.dto.request.DriverRequestDto;
import com.example.deliverymanagement.dto.response.DriverResponseDto;
import com.example.deliverymanagement.dto.response.ResponseDto;
import com.example.deliverymanagement.entity.Driver;

import java.util.List;

public interface DriverService {
    ResponseDto save(DriverRequestDto driverRequestDto);
    ResponseDto put(Driver driver);
    List<DriverResponseDto> getAll();
    DriverResponseDto getById(Long id);
    Driver getByEmail(String email);


}
