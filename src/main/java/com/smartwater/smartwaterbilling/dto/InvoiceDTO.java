package com.smartwater.smartwaterbilling.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDTO {

    private Long id;

    private Long waterBillId;

    private String consumerNumber;

    private Double unitsConsumed;

    private Double ratePerUnit;

    private Double totalAmount;

    private LocalDate billDate;

    private String billingCycle;

    private String status;
}