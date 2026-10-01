package com.smartwater.smartwaterbilling.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tariff_plans")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TariffPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String planName;

    // Rate for first baseLimit units
    @Column(nullable = false)
    private Double baseRate;

    // Rate after baseLimit units
    @Column(nullable = false)
    private Double higherRate;

    // Example: first 10 KL
    @Column(nullable = false)
    private Double baseLimit;
}