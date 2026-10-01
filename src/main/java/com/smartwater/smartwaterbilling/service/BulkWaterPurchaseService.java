package com.smartwater.smartwaterbilling.service;

import com.smartwater.smartwaterbilling.dto.BulkWaterPurchaseDTO;

import java.util.List;

public interface BulkWaterPurchaseService {

    BulkWaterPurchaseDTO createBulkWaterPurchase(
            BulkWaterPurchaseDTO dto);

    List<BulkWaterPurchaseDTO> getAllBulkWaterPurchases();

    BulkWaterPurchaseDTO getBulkWaterPurchaseById(
            Long id);

    BulkWaterPurchaseDTO updateBulkWaterPurchase(
            Long id,
            BulkWaterPurchaseDTO dto);

    void deleteBulkWaterPurchase(Long id);

    List<BulkWaterPurchaseDTO> getPurchasesBySupplier(
            String supplierName);

    List<BulkWaterPurchaseDTO> getPurchasesByStatus(
            String status);
}