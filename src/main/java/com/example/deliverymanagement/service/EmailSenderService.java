package com.example.deliverymanagement.service;

import com.example.deliverymanagement.dto.request.ConfirmationTokenRequestDto;
import com.example.deliverymanagement.dto.request.CustomerRequestDto;
import com.example.deliverymanagement.dto.request.DriverRequestDto;
import com.example.deliverymanagement.entity.ConfirmationToken;
import com.example.deliverymanagement.entity.ForgetPasswordToken;
import com.example.deliverymanagement.model.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderService {
    private final JavaMailSender mailSender;

    public void sendMail(CustomerRequestDto customerRequestDto, ConfirmationToken token,String text){
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setTo(customerRequestDto.getEmail());
        simpleMailMessage.setSubject("Confirm account");
        simpleMailMessage.setText(text+token.getToken());

        mailSender.send(simpleMailMessage);
        log.info("Message sent: "+customerRequestDto);
    }
    public void resetMail(ForgetPasswordToken token, String email, String text){
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Reset password");
        simpleMailMessage.setText(text+token.getToken());

        mailSender.send(simpleMailMessage);
        log.info("Message sent: "+token);
    }
    public void sendMailDriver(DriverRequestDto driverRequestDto,ConfirmationToken token){
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setTo(driverRequestDto.getEmail());
        simpleMailMessage.setSubject("Confirm account");
        simpleMailMessage.setText("/drivers/password-resets/"+token.getToken());

        mailSender.send(simpleMailMessage);
        log.info("Message sent: "+driverRequestDto);
    }

}
