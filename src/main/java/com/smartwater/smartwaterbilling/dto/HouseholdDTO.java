package com.smartwater.smartwaterbilling.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseholdDTO {

    private Long id;

    private String consumerNumber;

    private String householdName;

    private Double flatArea;

    private Integer occupants;
}