package com.smartwater.smartwaterbilling.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "bulk_water_purchases")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BulkWaterPurchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String supplierName;

    @Column(nullable = false)
    private Double quantity;

    @Column(nullable = false)
    private Double pricePerUnit;

    @Column(nullable = false)
    private Double totalAmount;

    @Column(nullable = false)
    private LocalDate purchaseDate;

    @Column(nullable = false)
    private String status;
}