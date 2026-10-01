package com.smartwater.smartwaterbilling.mapper;

import com.smartwater.smartwaterbilling.dto.BillingCycleDTO;
import com.smartwater.smartwaterbilling.entity.BillingCycle;

public class BillingCycleMapper {

    public static BillingCycleDTO toDTO(BillingCycle billingCycle) {

        if (billingCycle == null) {
            return null;
        }

        return new BillingCycleDTO(
                billingCycle.getId(),
                billingCycle.getCycleName(),
                billingCycle.getStartDate(),
                billingCycle.getEndDate(),
                billingCycle.getStatus()
        );
    }

    public static BillingCycle toEntity(BillingCycleDTO dto) {

        if (dto == null) {
            return null;
        }

        BillingCycle billingCycle = new BillingCycle();

        billingCycle.setId(dto.getId());
        billingCycle.setCycleName(dto.getCycleName());
        billingCycle.setStartDate(dto.getStartDate());
        billingCycle.setEndDate(dto.getEndDate());
        billingCycle.setStatus(dto.getStatus());

        return billingCycle;
    }
}