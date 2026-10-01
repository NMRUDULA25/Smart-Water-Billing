package com.smartwater.smartwaterbilling.service;

import com.smartwater.smartwaterbilling.dto.ConsumptionDistributionDTO;

import java.util.List;

public interface ConsumptionDistributionService {

    List<ConsumptionDistributionDTO> distributeCost(Long purchaseId);
}