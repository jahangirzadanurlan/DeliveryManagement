package com.example.deliverymanagement.service.impl;

import com.example.deliverymanagement.dto.response.ResponseDto;
import com.example.deliverymanagement.entity.ConfirmationToken;
import com.example.deliverymanagement.entity.ForgetPasswordToken;
import com.example.deliverymanagement.repository.ForgetPasswordTokenRepository;
import com.example.deliverymanagement.service.ForgetPasswordTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ForgetPasswordTokenImpl implements ForgetPasswordTokenService {
    private final ForgetPasswordTokenRepository tokenRepository;

    @Override
    public ResponseDto save(ForgetPasswordToken forgetPasswordToken) {
        ForgetPasswordToken save = tokenRepository.save(forgetPasswordToken);
        return save!=null ? new ResponseDto("Token Save is successfull"):
                new ResponseDto("Token save is unsuccessfull!!!");
    }

    @Override
    public ForgetPasswordToken getTokenByUUID(String uuid) {
        return tokenRepository.findForgetPasswordTokenByToken(uuid);
    }
}
