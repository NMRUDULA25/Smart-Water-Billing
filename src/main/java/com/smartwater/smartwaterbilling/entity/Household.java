package com.smartwater.smartwaterbilling.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "households")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Household {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String consumerNumber;

    @Column(nullable = false)
    private String householdName;

    @Column(nullable = false)
    private Double flatArea;

    @Column(nullable = false)
    private Integer occupants;
}