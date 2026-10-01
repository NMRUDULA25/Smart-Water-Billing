package com.smartwater.smartwaterbilling.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "apartments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String apartmentCode;

    @Column(nullable = false)
    private String apartmentName;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Integer totalHouseholds;
}