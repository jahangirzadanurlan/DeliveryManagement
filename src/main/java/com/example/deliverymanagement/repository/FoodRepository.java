package com.example.deliverymanagement.repository;

import com.example.deliverymanagement.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food,Long> {

}
