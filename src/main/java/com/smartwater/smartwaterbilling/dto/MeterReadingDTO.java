package com.smartwater.smartwaterbilling.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeterReadingDTO {

    private Long id;

    private String consumerNumber;

    private Double previousReading;

    private Double currentReading;

    private Double unitsConsumed;

    private String readingDate;
}