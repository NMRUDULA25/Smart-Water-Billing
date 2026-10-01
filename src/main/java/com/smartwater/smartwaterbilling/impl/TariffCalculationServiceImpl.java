package com.smartwater.smartwaterbilling.impl;

import com.smartwater.smartwaterbilling.entity.TariffPlan;
import com.smartwater.smartwaterbilling.service.TariffCalculationService;
import org.springframework.stereotype.Service;

@Service
public class TariffCalculationServiceImpl implements TariffCalculationService {

    @Override
    public Double calculateBill(Double unitsConsumed, TariffPlan tariffPlan) {

        if (unitsConsumed == null || unitsConsumed < 0) {
            throw new RuntimeException("Units consumed cannot be negative");
        }

        if (unitsConsumed <= tariffPlan.getBaseLimit()) {

            return unitsConsumed * tariffPlan.getBaseRate();

        } else {

            double baseUnits = tariffPlan.getBaseLimit();

            double higherUnits = unitsConsumed - baseUnits;

            return (baseUnits * tariffPlan.getBaseRate())
                    + (higherUnits * tariffPlan.getHigherRate());
        }
    }
}