package com.smartwater.smartwaterbilling.repository;

import com.smartwater.smartwaterbilling.entity.Household;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HouseholdRepository
        extends JpaRepository<Household, Long> {

    Optional<Household> findByConsumerNumber(
            String consumerNumber);
}