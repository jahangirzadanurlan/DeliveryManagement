package com.example.deliverymanagement.entity;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Integer count;
    Double totalAmount;

    @OneToOne
    Customer customer;
    @OneToMany
    List<Delivery_order> deliveryorders;
    @OneToMany
    List<Food> foods;

}
