package com.example.deliverymanagement.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Delivery_order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String place;
    @Size(max = 16,message = "cart must contain 16 character")
    String cardNumber;
    @Builder.Default
    Integer status=1;
    Date date;

    @OneToOne
    Driver driver;
    @ManyToOne
    Cart cart;
    @ManyToOne
    Customer customer;

}
