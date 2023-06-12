package com.example.deliverymanagement.controller;

import com.example.deliverymanagement.dto.response.CustomerResponseDto;
import com.example.deliverymanagement.dto.response.DriverResponseDto;
import com.example.deliverymanagement.dto.response.ResponseDto;
import com.example.deliverymanagement.entity.Driver;
import com.example.deliverymanagement.service.CustomerService;
import com.example.deliverymanagement.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final DriverService driverService;
    private final CustomerService customerService;
    private final ModelMapper modelMapper;

    @GetMapping("/drivers")
    public List<DriverResponseDto> getAllDrivers(){
        return driverService.getAll();
    }
    @GetMapping("/drivers/{id}")
    public DriverResponseDto driverById(@PathVariable Long id){
        return modelMapper.map(driverService.getById(id),DriverResponseDto.class);
    }
    @PutMapping("/drivers/{id}")
    public ResponseDto updateDriver(@PathVariable Long id,@RequestBody DriverResponseDto driverResponseDto){
        Driver driver=driverService.getById(id);
        if (driver!=null){
            driver.setName(driverResponseDto.getName());
            driver.setEmail(driverResponseDto.getEmail());
            driver.setPassword(driverResponseDto.getPassword());
            driverService.put(driver);
            return new ResponseDto("Update is successfully!");
        }else {
            return new ResponseDto("Driver id is wrong!!!");
        }
    }
    @DeleteMapping("/drivers/{id}")
    public ResponseDto deleteDriver(@PathVariable Long id){
        return driverService.delete(id);
    }
    @GetMapping("/customers")
    public List<CustomerResponseDto> getAllCustomers(){
        return customerService.getAll();
    }
    @GetMapping("/customers/{id}")
    public CustomerResponseDto getCustomer(@PathVariable Long id){
        return customerService.findById(id);
    }
}
