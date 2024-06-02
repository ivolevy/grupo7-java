package com.example.uade.tpo.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class PaymentResponseDto {
    Long id;
    Long OrderId;
    Double amount;
    Date date;
    String status;
    Long paymentMethodId;
}
