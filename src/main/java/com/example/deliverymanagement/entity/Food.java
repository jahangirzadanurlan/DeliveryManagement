package com.example.deliverymanagement.entity;

import javax.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String description;
    Double amount;

    @Embedded
    FoodDetails foodDetails;

    @ManyToOne
    Category category;
    @ManyToOne
    @ToString.Exclude
    Cart cart;

}
