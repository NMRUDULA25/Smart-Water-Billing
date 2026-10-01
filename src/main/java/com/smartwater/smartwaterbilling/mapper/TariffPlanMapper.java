package com.smartwater.smartwaterbilling.mapper;

import com.smartwater.smartwaterbilling.dto.TariffPlanDTO;
import com.smartwater.smartwaterbilling.entity.TariffPlan;

public class TariffPlanMapper {

    public static TariffPlan toEntity(TariffPlanDTO dto) {

        if (dto == null) {
            return null;
        }

        TariffPlan tariffPlan = new TariffPlan();

        tariffPlan.setId(dto.getId());
        tariffPlan.setPlanName(dto.getPlanName());
        tariffPlan.setBaseRate(dto.getBaseRate());
        tariffPlan.setHigherRate(dto.getHigherRate());
        tariffPlan.setBaseLimit(dto.getBaseLimit());

        return tariffPlan;
    }

    public static TariffPlanDTO toDTO(TariffPlan entity) {

        if (entity == null) {
            return null;
        }

        return new TariffPlanDTO(
                entity.getId(),
                entity.getPlanName(),
                entity.getBaseRate(),
                entity.getHigherRate(),
                entity.getBaseLimit()
        );
    }
}