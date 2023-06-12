package com.example.deliverymanagement.dto.request;

import com.example.deliverymanagement.entity.Food;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FoodRequestDto {
    String name;
    String description;
    Double amount;
}
