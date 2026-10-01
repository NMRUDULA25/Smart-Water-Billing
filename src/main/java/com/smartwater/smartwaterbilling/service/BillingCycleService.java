package com.smartwater.smartwaterbilling.service;

import com.smartwater.smartwaterbilling.dto.BillingCycleDTO;

import java.util.List;

public interface BillingCycleService {

    BillingCycleDTO createBillingCycle(BillingCycleDTO dto);

    BillingCycleDTO getBillingCycleById(Long id);

    List<BillingCycleDTO> getAllBillingCycles();

    BillingCycleDTO updateBillingCycle(Long id, BillingCycleDTO dto);

    void deleteBillingCycle(Long id);

    BillingCycleDTO openBillingCycle(Long id);

    BillingCycleDTO finalizeBillingCycle(Long id);

    BillingCycleDTO archiveBillingCycle(Long id);
}