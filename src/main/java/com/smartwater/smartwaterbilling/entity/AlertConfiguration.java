package com.smartwater.smartwaterbilling.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "alert_configurations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlertConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double overuseThreshold;

    @Column(nullable = false)
    private Double anomalyStandardDeviationMultiplier;

    @Column(nullable = false)
    private Boolean emailEnabled;

    @Column(nullable = false)
    private Boolean inAppEnabled;

    @Column(nullable = false)
    private Boolean active;
}