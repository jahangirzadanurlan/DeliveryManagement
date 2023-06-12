package com.example.deliverymanagement.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConfirmationTokenRequestDto {
    String token;
    Date createdAt;
    Date confirmedAt;
    Boolean confirm;
}
