package com.smartwater.smartwaterbilling.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "water_bills")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WaterBill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String consumerNumber;

    @Column(nullable = false)
    private Double unitsConsumed;

    @Column(nullable = false)
    private Double ratePerUnit;

    @Column(nullable = false)
    private Double totalAmount;

    @Column(nullable = false)
    private LocalDate billDate;

    @Column(nullable = false)
    private String billingCycle;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "tariff_plan_id", nullable = false)
    private TariffPlan tariffPlan;
}