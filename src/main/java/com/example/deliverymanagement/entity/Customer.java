package com.example.deliverymanagement.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.criterion.Order;

import javax.persistence.Entity;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String surname;
    String birthdate;

    @Column(unique = true)
    @Email(message = "Invalid email format")
    String email;

    @Size(min = 8,message = "Password must be at least 8 characters")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$",message ="Password must contain the characters A-Za-z0-9" )
    String password;
    String phoneNumber;
    @Builder.Default
    Boolean isEnabled=false;
    @Builder.Default
    Boolean resetEnabled=false;

    @OneToOne(mappedBy = "customer",fetch = FetchType.LAZY)
    @ToString.Exclude
    Cart cart;

    @OneToMany
    List<Role> roles;

    @OneToMany(mappedBy = "customer",fetch = FetchType.LAZY)
    List<Delivery_order> orders;


}
