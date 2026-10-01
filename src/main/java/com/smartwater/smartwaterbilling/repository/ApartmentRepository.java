package com.smartwater.smartwaterbilling.repository;

import com.smartwater.smartwaterbilling.entity.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

    boolean existsByApartmentCode(String apartmentCode);
}