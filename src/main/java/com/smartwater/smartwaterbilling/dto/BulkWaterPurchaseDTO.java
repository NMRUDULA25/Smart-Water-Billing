package com.smartwater.smartwaterbilling.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BulkWaterPurchaseDTO {

    private Long id;

    private String supplierName;

    private Double quantity;

    private Double pricePerUnit;

    private Double totalAmount;

    private LocalDate purchaseDate;

    private String status;
}