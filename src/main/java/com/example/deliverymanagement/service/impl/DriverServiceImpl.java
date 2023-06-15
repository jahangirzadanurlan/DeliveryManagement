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
    public ResponseDto delete(Long id) {
        Driver driver=driverRepository.getDriverById(id);
        if (driver!=null){
            driverRepository.delete(driver);
            return new ResponseDto("Deleted is successfully!");
        }else {
            return new ResponseDto("Driver id is wrong!!!");
        }
    }

    @Override
    public List<DriverResponseDto> getAll() {
        return driverRepository.findAll().stream()
                .map(driver -> modelMapper.map(driver,DriverResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Driver getById(Long id) {
        return driverRepository.getDriverById(id);
    }

    @Override
    public Driver getByEmail(String email) {
        return driverRepository.getDriverByEmail(email);
    }

    @Override
    public Driver search() {
        List<Driver> drivers=driverRepository.findAll();
        Driver driver=drivers.stream()
                .filter(driver1 -> !driver1.getIsBusy())
                .findFirst()
                .orElse(null);
        driver.setIsBusy(true);
        driverRepository.save(driver);
        return driver;
    }
}
