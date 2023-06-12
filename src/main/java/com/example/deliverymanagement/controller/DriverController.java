package com.example.deliverymanagement.controller;

import com.example.deliverymanagement.dto.request.DriverRequestDto;
import com.example.deliverymanagement.dto.response.DriverResponseDto;
import com.example.deliverymanagement.dto.response.ResponseDto;
import com.example.deliverymanagement.entity.ConfirmationToken;
import com.example.deliverymanagement.entity.Driver;
import com.example.deliverymanagement.service.ConfirmationTokenService;
import com.example.deliverymanagement.service.DriverService;
import com.example.deliverymanagement.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/driver")
public class DriverController {
    private final DriverService driverService;
    private final EmailSenderService emailSenderService;
    private final ConfirmationTokenService tokenService;

    @PostMapping
    public ResponseDto save(@RequestBody DriverRequestDto driverRequestDto){
        ResponseDto save = driverService.save(driverRequestDto);
        Driver driver=driverService.getByEmail(driverRequestDto.getEmail());
        ConfirmationToken token= ConfirmationToken.builder()
                .token(UUID.randomUUID().toString())
                .createdAt(Date.valueOf(LocalDate.now()))
                .driver(driver)
                .build();
        tokenService.save(token);
        emailSenderService.sendMailDriver(driverRequestDto,token);
        return save!=null? new ResponseDto("Waiting for the driver to verify his account!"):
                new ResponseDto("Save is unsuccessfull!");
    }
    @GetMapping("/password-reset/{uuid}")
    public ResponseDto confirmDriver(@PathVariable UUID uuid){
        ConfirmationToken token=tokenService.getTokenByUUID(uuid.toString());
        if (token!=null){
            token.setConfirmedAt(Date.valueOf(LocalDate.now()));
            tokenService.save(token);
            Driver driver=token.getDriver();
            driver.setIsResetPsw(true);
            driverService.put(driver);
            return new ResponseDto("Your account has been verified");
        }else {
            return new ResponseDto("Link is wrong!!!");
        }
    }
    @PutMapping("/password")
    public ResponseDto resetPassword(@RequestBody Map<String,String> response){
        String email=response.get("email");
        String password=response.get("password");

        Driver driver=driverService.getByEmail(email);
        if (driver!=null && driver.getIsResetPsw().equals(true)){
            driver.setPassword(password);
            driver.setIsEnabled(true);
            driverService.put(driver);
            return new ResponseDto("Password changed successfully! Your account has been activated");
        }else {
            return new ResponseDto("Driver not found!!!");
        }
    }
    @GetMapping("/user")
    public DriverResponseDto driver(){
        //security hissesinde daxil elediyi ad bazada yoxlanilmali ve onun melumatlari gorsedilmelidir
        return null;
    }

}
