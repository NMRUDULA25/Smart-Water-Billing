package com.smartwater.smartwaterbilling.repository;

import com.smartwater.smartwaterbilling.entity.TariffPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TariffPlanRepository extends JpaRepository<TariffPlan, Long> {
}