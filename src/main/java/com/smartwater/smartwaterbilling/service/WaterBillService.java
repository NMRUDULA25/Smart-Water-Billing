package com.smartwater.smartwaterbilling.service;

import com.smartwater.smartwaterbilling.dto.WaterBillDTO;

import java.util.List;

public interface WaterBillService {

    WaterBillDTO createWaterBill(
            WaterBillDTO waterBillDTO
    );

    WaterBillDTO getWaterBillById(
            Long id
    );

    List<WaterBillDTO> getAllWaterBills();

    WaterBillDTO updateWaterBill(
            Long id,
            WaterBillDTO waterBillDTO
    );

    void deleteWaterBill(
            Long id
    );

    List<WaterBillDTO> getBillsByConsumerNumber(
            String consumerNumber
    );

    List<WaterBillDTO> getBillsByBillingCycle(
            String billingCycle
    );

    List<WaterBillDTO> getBillsByStatus(
            String status
    );

    WaterBillDTO markBillAsPaid(
            Long id
    );
}