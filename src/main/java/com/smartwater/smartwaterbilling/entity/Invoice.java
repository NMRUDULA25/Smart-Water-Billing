package com.smartwater.smartwaterbilling.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "invoices")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long waterBillId;

    @Column(nullable = false)
    private String consumerNumber;

    @Column(nullable = false)
    private String billingCycle;

    @Column(nullable = false)
    private Double unitsConsumed;

    @Column(nullable = false)
    private Double ratePerUnit;

    @Column(nullable = false)
    private Double baseCharge;

    @Column(nullable = false)
    private Double sharedCostAllocation;

    @Column(nullable = false)
    private Double adjustment;

    @Column(nullable = false)
    private Double totalAmount;

    @Column(nullable = false)
    private LocalDate invoiceDate;

    @Column(nullable = false)
    private String status;
}