package com.example.deliverymanagement.service;

import com.example.deliverymanagement.dto.response.ResponseDto;
import com.example.deliverymanagement.entity.ConfirmationToken;

public interface ConfirmationTokenService {
    ResponseDto save(ConfirmationToken confirmationToken);
    ConfirmationToken getTokenByUUID(String uuid);

}
