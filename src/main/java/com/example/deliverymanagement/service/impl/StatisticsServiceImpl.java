package com.example.deliverymanagement.service.impl;

import com.example.deliverymanagement.dto.response.StatisticsResponseDto;
import com.example.deliverymanagement.entity.Delivery_order;
import com.example.deliverymanagement.repository.CustomerRepository;
import com.example.deliverymanagement.repository.DriverRepository;
import com.example.deliverymanagement.repository.OrderRepository;
import com.example.deliverymanagement.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final DriverRepository driverRepository;
    @Override
    public StatisticsResponseDto dashboard() {
        Integer customerCount=customerRepository.findAll().size();
        Integer driverCount=driverRepository.findAll().size();
        Double dailyIncome=orderRepository.findAll().stream()
                .filter(order->order.getDate().getDay()==LocalDate.now().getDayOfMonth())
                .mapToDouble(order->order.getCart().getTotalAmount())
                .sum();
        Long dailyOrderCount=orderRepository.findAll().stream()
                .filter(order->order.getDate().getDay()==LocalDate.now().getDayOfMonth())
                .count();
        StatisticsResponseDto statistics = StatisticsResponseDto.builder()
                .customerCount(customerCount)
                .driverCount(driverCount)
                .dailyOrderCount(dailyOrderCount)
                .dailyIncome(dailyIncome)
                .build();
        if (statistics!=null){
            return statistics;
        }else {
            throw new RuntimeException("Dont found Any statistics");
        }
    }
}
