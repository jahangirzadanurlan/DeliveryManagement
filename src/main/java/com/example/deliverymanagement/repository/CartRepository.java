package com.example.deliverymanagement.repository;

import com.example.deliverymanagement.entity.Cart;
import com.example.deliverymanagement.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findCartByCustomer(Customer customer);

}
