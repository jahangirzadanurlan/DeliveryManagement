package com.example.deliverymanagement.service.impl;

import com.example.deliverymanagement.dto.request.DriverRequestDto;
import com.example.deliverymanagement.dto.response.DriverResponseDto;
import com.example.deliverymanagement.dto.response.ResponseDto;
import com.example.deliverymanagement.entity.Driver;
import com.example.deliverymanagement.repository.DriverRepository;
import com.example.deliverymanagement.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseDto save(DriverRequestDto driverRequestDto) {
        Driver save = driverRepository.save(modelMapper.map(driverRequestDto, Driver.class));
        return save!=null? new ResponseDto("Save is successfull!"):
                new ResponseDto("Save is unsuccessfully!!!");
    }

    @Override
    public ResponseDto put(Driver driver) {
        Driver save = driverRepository.save(driver);
        return save!=null? new ResponseDto("Save is successfull!"):
                new ResponseDto("Save is unsuccessfully!!!");
    }

    @Override
    public List<DriverResponseDto> getAll() {
        return driverRepository.findAll().stream()
                .map(driver -> modelMapper.map(driver,DriverResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public DriverResponseDto getById(Long id) {
        return modelMapper.map(driverRepository.getDriverById(id),DriverResponseDto.class);
    }

    @Override
    public Driver getByEmail(String email) {
        return driverRepository.getDriverByEmail(email);
    }
}
