package com.example.deliverymanagement.repository;

import com.example.deliverymanagement.entity.ForgetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgetPasswordTokenRepository extends JpaRepository<ForgetPasswordToken,Long> {
    ForgetPasswordToken findForgetPasswordTokenByToken(String uuid);

}
