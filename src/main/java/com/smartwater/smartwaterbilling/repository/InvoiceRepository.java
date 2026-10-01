package com.smartwater.smartwaterbilling.repository;

import com.smartwater.smartwaterbilling.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findByConsumerNumber(
            String consumerNumber
    );

    List<Invoice> findByBillingCycle(
            String billingCycle
    );

    List<Invoice> findByStatus(
            String status
    );

    List<Invoice> findByConsumerNumberAndBillingCycle(
            String consumerNumber,
            String billingCycle
    );

    Optional<Invoice> findByWaterBillId(
            Long waterBillId
    );
}