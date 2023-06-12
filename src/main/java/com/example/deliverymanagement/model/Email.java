package com.example.deliverymanagement.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Email {
    String from;
    String to;
    String subject;
    String body;
}
