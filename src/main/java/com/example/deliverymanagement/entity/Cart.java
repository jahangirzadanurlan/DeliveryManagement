package com.example.deliverymanagement.entity;

import javax.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Builder.Default
    Integer count=0;
    @Builder.Default
    Double totalAmount=0.0;

    @OneToOne
    Customer customer;
    @OneToMany
    List<Delivery_order> deliveryorders;
    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL)
    List<Food> foods;

}
