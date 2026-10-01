package com.smartwater.smartwaterbilling.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    private Long id;

    private Long billId;

    private String consumerNumber;

    private Double amount;

    private LocalDate paymentDate;

    private String paymentMethod;

    private String status;
}