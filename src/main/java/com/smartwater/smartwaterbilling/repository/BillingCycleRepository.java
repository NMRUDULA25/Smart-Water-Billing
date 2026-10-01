package com.smartwater.smartwaterbilling.repository;

import com.smartwater.smartwaterbilling.entity.BillingCycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingCycleRepository extends JpaRepository<BillingCycle, Long> {
}