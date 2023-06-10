package com.example.deliverymanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapper {

    @Bean
    ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
