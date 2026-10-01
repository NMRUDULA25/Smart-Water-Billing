package com.smartwater.smartwaterbilling.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumptionDistributionDTO {

    private String consumerNumber;

    private Double unitsConsumed;

    private Double consumptionPercentage;

    private Double allocatedAmount;
}