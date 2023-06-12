package com.example.deliverymanagement.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;

    @Column(unique = true)
    @Email(message = "Invalid email format")
    String email;

    @Size(min = 8,message = "Password must be at least 8 characters")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$",message ="Password must contain the characters A-Za-z0-9" )
    String password;
    @Builder.Default
    Boolean isBusy=false;
    @Builder.Default
    Boolean isEnabled=false;
    @Builder.Default
    Boolean isResetPsw=false;
    @OneToOne
    Customer customer;
}
