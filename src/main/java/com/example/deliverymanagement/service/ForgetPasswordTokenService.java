package com.example.deliverymanagement.service;

import com.example.deliverymanagement.dto.response.ResponseDto;
import com.example.deliverymanagement.entity.ForgetPasswordToken;

public interface ForgetPasswordTokenService {
    ResponseDto save(ForgetPasswordToken forgetPasswordToken);
    ForgetPasswordToken getTokenByUUID(String uuid);
}
