package com.smartwater.smartwaterbilling.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillingCycleDTO {

    private Long id;
    private String cycleName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
}