package com.example.deliverymanagement.repository;

import com.example.deliverymanagement.entity.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken,Long> {
    ConfirmationToken findConfirmationTokenByToken(String uuid);
}
