package com.example.deliverymanagement.repository;

import com.example.deliverymanagement.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver,Long> {
    Driver getDriverById(Long id);
    Driver getDriverByEmail(String email);
}
