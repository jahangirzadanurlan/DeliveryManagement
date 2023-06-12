package com.example.deliverymanagement.service.impl;

import com.example.deliverymanagement.dto.request.ConfirmationTokenRequestDto;
import com.example.deliverymanagement.dto.response.ResponseDto;
import com.example.deliverymanagement.entity.ConfirmationToken;
import com.example.deliverymanagement.repository.ConfirmationTokenRepository;
import com.example.deliverymanagement.service.ConfirmationTokenService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {
    private final ConfirmationTokenRepository tokenRepository;

    @Override
    public ResponseDto save(ConfirmationToken confirmationToken) {
        ConfirmationToken save = tokenRepository.save(confirmationToken);
        return save!=null ? new ResponseDto("Token Save is successfull"):
                new ResponseDto("Token save is unsuccessfull!!!");
    }

    @Override
    public ConfirmationToken getTokenByUUID(String uuid) {
        return tokenRepository.findConfirmationTokenByToken(uuid);
    }
}
