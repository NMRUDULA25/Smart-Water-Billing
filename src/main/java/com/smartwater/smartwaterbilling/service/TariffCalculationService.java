package com.smartwater.smartwaterbilling.service;

import com.smartwater.smartwaterbilling.entity.TariffPlan;

public interface TariffCalculationService {

    Double calculateBill(Double unitsConsumed, TariffPlan tariffPlan);

}