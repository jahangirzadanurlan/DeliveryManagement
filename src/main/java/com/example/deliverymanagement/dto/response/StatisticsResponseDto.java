package com.example.deliverymanagement.dto.response;

import lombok.Builder;

@Builder
public class StatisticsResponseDto {
    Integer customerCount;
    Integer driverCount;
    Double dailyIncome;
    Long dailyOrderCount;
}
