package com.smartwater.smartwaterbilling.repository;

import com.smartwater.smartwaterbilling.entity.BulkWaterPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BulkWaterPurchaseRepository
        extends JpaRepository<BulkWaterPurchase, Long> {

    List<BulkWaterPurchase> findBySupplierNameIgnoreCase(
            String supplierName);

    List<BulkWaterPurchase> findByStatusIgnoreCase(
            String status);
}