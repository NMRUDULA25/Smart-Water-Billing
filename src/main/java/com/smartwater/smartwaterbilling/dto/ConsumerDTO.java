package com.smartwater.smartwaterbilling.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerDTO {

    private Long id;
    private String consumerNumber;
    private String ownerName;
    private String address;
    private String phone;
    private String connectionType;
}