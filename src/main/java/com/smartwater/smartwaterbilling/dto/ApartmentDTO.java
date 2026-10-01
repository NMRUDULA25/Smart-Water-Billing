package com.smartwater.smartwaterbilling.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApartmentDTO {

    private Long id;

    @NotBlank(message = "Apartment code is required")
    private String apartmentCode;

    @NotBlank(message = "Apartment name is required")
    private String apartmentName;

    @NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "Total households is required")
    @Min(value = 1, message = "Total households must be at least 1")
    private Integer totalHouseholds;
}