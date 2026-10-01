package com.smartwater.smartwaterbilling.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnomalyDTO {

    private String consumerNumber;

    private Double previousConsumption;

    private Double currentConsumption;

    private Double increasePercentage;

    private String status;

    private String message;
}