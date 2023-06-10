package com.example.deliverymanagement.entity;

import jakarta.persistence.*;
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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String surname;
    String birthdate;
    String email;
    String password;
    String phoneNumber;
    Boolean isEnabled;

    @OneToMany
    List<Food> foods;
    @OneToOne
    Cart cart;
    @OneToMany
    List<Role> roles;



}
