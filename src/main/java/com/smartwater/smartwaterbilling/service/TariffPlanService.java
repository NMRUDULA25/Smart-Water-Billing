package com.smartwater.smartwaterbilling.service;

import com.smartwater.smartwaterbilling.dto.TariffPlanDTO;

import java.util.List;

public interface TariffPlanService {

    TariffPlanDTO createTariffPlan(TariffPlanDTO dto);

    TariffPlanDTO getTariffPlanById(Long id);

    List<TariffPlanDTO> getAllTariffPlans();

    TariffPlanDTO updateTariffPlan(Long id, TariffPlanDTO dto);

    void deleteTariffPlan(Long id);
}