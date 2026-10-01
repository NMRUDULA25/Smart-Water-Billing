package com.smartwater.smartwaterbilling.mapper;

import com.smartwater.smartwaterbilling.dto.WaterBillDTO;
import com.smartwater.smartwaterbilling.entity.TariffPlan;
import com.smartwater.smartwaterbilling.entity.WaterBill;

public class WaterBillMapper {

    // Entity -> DTO
    public static WaterBillDTO toDTO(WaterBill waterBill) {

        if (waterBill == null) {
            return null;
        }

        Long tariffPlanId = null;

        if (waterBill.getTariffPlan() != null) {
            tariffPlanId = waterBill.getTariffPlan().getId();
        }

        return new WaterBillDTO(
                waterBill.getId(),
                waterBill.getConsumerNumber(),
                waterBill.getUnitsConsumed(),
                waterBill.getRatePerUnit(),
                waterBill.getTotalAmount(),
                waterBill.getBillDate(),
                waterBill.getBillingCycle(),
                waterBill.getStatus(),
                tariffPlanId
        );
    }

    // DTO -> Entity
    public static WaterBill toEntity(
            WaterBillDTO dto,
            TariffPlan tariffPlan) {

        if (dto == null) {
            return null;
        }

        WaterBill waterBill = new WaterBill();

        waterBill.setId(dto.getId());
        waterBill.setConsumerNumber(dto.getConsumerNumber());
        waterBill.setUnitsConsumed(dto.getUnitsConsumed());
        waterBill.setRatePerUnit(dto.getRatePerUnit());
        waterBill.setTotalAmount(dto.getTotalAmount());
        waterBill.setBillDate(dto.getBillDate());
        waterBill.setBillingCycle(dto.getBillingCycle());
        waterBill.setStatus(dto.getStatus());
        waterBill.setTariffPlan(tariffPlan);

        return waterBill;
    }
}