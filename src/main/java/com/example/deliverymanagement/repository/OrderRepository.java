package com.example.deliverymanagement.repository;

import com.example.deliverymanagement.entity.Delivery_order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Delivery_order,Long> {

}
