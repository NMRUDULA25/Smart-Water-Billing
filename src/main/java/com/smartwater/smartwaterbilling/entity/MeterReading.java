package com.smartwater.smartwaterbilling.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "meter_readings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeterReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String consumerNumber;

    @Column(nullable = false)
    private Double previousReading;

    @Column(nullable = false)
    private Double currentReading;

    @Column(nullable = false)
    private Double unitsConsumed;

    @Column(nullable = false)
    private String readingDate;
}