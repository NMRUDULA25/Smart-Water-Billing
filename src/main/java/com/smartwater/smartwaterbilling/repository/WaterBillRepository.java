package com.smartwater.smartwaterbilling.repository;

import com.smartwater.smartwaterbilling.entity.WaterBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WaterBillRepository
        extends JpaRepository<WaterBill, Long> {

    List<WaterBill> findByConsumerNumber(
            String consumerNumber
    );

    List<WaterBill> findByBillingCycle(
            String billingCycle
    );

    List<WaterBill> findByStatus(
            String status
    );

    List<WaterBill> findByConsumerNumberAndBillingCycle(
            String consumerNumber,
            String billingCycle
    );

    long countByStatus(
            String status
    );
}