package com.example.deliverymanagement.repository;

import com.example.deliverymanagement.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Customer findCustomerByEmail(String email);
    Customer getCustomerById(Long id);

}
