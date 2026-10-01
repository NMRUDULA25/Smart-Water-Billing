package com.smartwater.smartwaterbilling.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "consumers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Consumer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String consumerNumber;

    @Column(nullable = false)
    private String ownerName;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false)
    private String connectionType;
}