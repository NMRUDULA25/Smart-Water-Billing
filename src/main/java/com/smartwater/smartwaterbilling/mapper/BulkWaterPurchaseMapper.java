package com.smartwater.smartwaterbilling.mapper;

import com.smartwater.smartwaterbilling.dto.BulkWaterPurchaseDTO;
import com.smartwater.smartwaterbilling.entity.BulkWaterPurchase;

public class BulkWaterPurchaseMapper {

    public static BulkWaterPurchaseDTO toDTO(
            BulkWaterPurchase purchase) {

        if (purchase == null) {
            return null;
        }

        return new BulkWaterPurchaseDTO(
                purchase.getId(),
                purchase.getSupplierName(),
                purchase.getQuantity(),
                purchase.getPricePerUnit(),
                purchase.getTotalAmount(),
                purchase.getPurchaseDate(),
                purchase.getStatus()
        );
    }

    public static BulkWaterPurchase toEntity(
            BulkWaterPurchaseDTO dto) {

        if (dto == null) {
            return null;
        }

        BulkWaterPurchase purchase =
                new BulkWaterPurchase();

        purchase.setId(dto.getId());
        purchase.setSupplierName(dto.getSupplierName());
        purchase.setQuantity(dto.getQuantity());
        purchase.setPricePerUnit(dto.getPricePerUnit());
        purchase.setTotalAmount(dto.getTotalAmount());
        purchase.setPurchaseDate(dto.getPurchaseDate());
        purchase.setStatus(dto.getStatus());

        return purchase;
    }
}