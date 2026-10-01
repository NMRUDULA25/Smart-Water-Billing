package com.smartwater.smartwaterbilling.impl;

import com.smartwater.smartwaterbilling.dto.ConsumptionDistributionDTO;
import com.smartwater.smartwaterbilling.entity.BulkWaterPurchase;
import com.smartwater.smartwaterbilling.entity.Household;
import com.smartwater.smartwaterbilling.entity.MeterReading;
import com.smartwater.smartwaterbilling.repository.BulkWaterPurchaseRepository;
import com.smartwater.smartwaterbilling.repository.HouseholdRepository;
import com.smartwater.smartwaterbilling.repository.MeterReadingRepository;
import com.smartwater.smartwaterbilling.service.ConsumptionDistributionService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ConsumptionDistributionServiceImpl
        implements ConsumptionDistributionService {

    private final BulkWaterPurchaseRepository bulkWaterPurchaseRepository;
    private final MeterReadingRepository meterReadingRepository;
    private final HouseholdRepository householdRepository;

    public ConsumptionDistributionServiceImpl(
            BulkWaterPurchaseRepository bulkWaterPurchaseRepository,
            MeterReadingRepository meterReadingRepository,
            HouseholdRepository householdRepository) {

        this.bulkWaterPurchaseRepository = bulkWaterPurchaseRepository;
        this.meterReadingRepository = meterReadingRepository;
        this.householdRepository = householdRepository;
    }

    @Override
    public List<ConsumptionDistributionDTO> distributeCost(
            Long purchaseId) {

        // ---------------------------------------------------------
        // 1. Get bulk water purchase
        // ---------------------------------------------------------

        BulkWaterPurchase purchase =
                bulkWaterPurchaseRepository.findById(purchaseId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Bulk water purchase not found"));

        Double totalCost = purchase.getTotalAmount();

        if (totalCost == null || totalCost <= 0) {
            throw new RuntimeException(
                    "Bulk water purchase total amount must be greater than zero");
        }

        // ---------------------------------------------------------
        // 2. Get all households
        // ---------------------------------------------------------

        List<Household> households =
                householdRepository.findAll();

        if (households.isEmpty()) {
            throw new RuntimeException(
                    "No households available for distribution");
        }

        // ---------------------------------------------------------
        // 3. Get all meter readings
        // ---------------------------------------------------------

        List<MeterReading> readings =
                meterReadingRepository.findAll();

        /*
         * Keep only the latest valid reading for each consumer.
         */
        Map<String, MeterReading> latestReadings =
                readings.stream()
                        .filter(r ->
                                r.getConsumerNumber() != null &&
                                        r.getUnitsConsumed() != null)
                        .collect(Collectors.toMap(
                                MeterReading::getConsumerNumber,
                                r -> r,
                                (r1, r2) ->
                                        r1.getId() > r2.getId()
                                                ? r1
                                                : r2
                        ));

        // ---------------------------------------------------------
        // 4. Separate metered and unmetered households
        // ---------------------------------------------------------

        List<Household> meteredHouseholds =
                households.stream()
                        .filter(h ->
                                h.getConsumerNumber() != null &&
                                        latestReadings.containsKey(
                                                h.getConsumerNumber()))
                        .collect(Collectors.toList());

        List<Household> unmeteredHouseholds =
                households.stream()
                        .filter(h ->
                                h.getConsumerNumber() == null ||
                                        !latestReadings.containsKey(
                                                h.getConsumerNumber()))
                        .collect(Collectors.toList());

        // ---------------------------------------------------------
        // 5. Calculate total metered consumption
        // ---------------------------------------------------------

        double totalConsumption =
                meteredHouseholds.stream()
                        .map(h ->
                                latestReadings.get(
                                        h.getConsumerNumber()))
                        .filter(Objects::nonNull)
                        .mapToDouble(
                                MeterReading::getUnitsConsumed)
                        .sum();

        // ---------------------------------------------------------
        // 6. Calculate total flat area of unmetered households
        // ---------------------------------------------------------

        double totalUnmeteredFlatArea =
                unmeteredHouseholds.stream()
                        .filter(h -> h.getFlatArea() != null)
                        .mapToDouble(Household::getFlatArea)
                        .sum();

        // ---------------------------------------------------------
        // 7. Create result
        // ---------------------------------------------------------

        List<ConsumptionDistributionDTO> result =
                new ArrayList<>();

        /*
         * CASE 1:
         *
         * We have both metered and unmetered households.
         *
         * Metered households receive their share according
         * to consumption.
         *
         * Unmetered households receive their share according
         * to flat area.
         */
        if (totalConsumption > 0 &&
                !meteredHouseholds.isEmpty() &&
                !unmeteredHouseholds.isEmpty()) {

            /*
             * Allocate the cost between the two groups.
             *
             * For the metered group:
             * allocation is based on consumption.
             *
             * For the unmetered group:
             * allocation is based on flat area.
             *
             * The total cost is divided between the groups
             * according to their respective weights.
             */

            double totalFlatArea =
                    households.stream()
                            .filter(h -> h.getFlatArea() != null)
                            .mapToDouble(Household::getFlatArea)
                            .sum();

            /*
             * If flat-area information is unavailable,
             * fall back to consumption-only distribution.
             */
            if (totalFlatArea <= 0) {

                for (Household household :
                        meteredHouseholds) {

                    MeterReading reading =
                            latestReadings.get(
                                    household.getConsumerNumber());

                    double units =
                            reading.getUnitsConsumed();

                    double percentage =
                            (units / totalConsumption) * 100;

                    double allocatedAmount =
                            (units / totalConsumption) * totalCost;

                    result.add(
                            new ConsumptionDistributionDTO(
                                    household.getConsumerNumber(),
                                    units,
                                    percentage,
                                    allocatedAmount
                            )
                    );
                }

            } else {

                /*
                 * Calculate the total weight.
                 *
                 * Metered households use consumption.
                 * Unmetered households use flat area.
                 */

                double totalWeight =
                        totalConsumption +
                                totalUnmeteredFlatArea;

                /*
                 * Metered households
                 */

                for (Household household :
                        meteredHouseholds) {

                    MeterReading reading =
                            latestReadings.get(
                                    household.getConsumerNumber());

                    double units =
                            reading.getUnitsConsumed();

                    double percentage =
                            (units / totalWeight) * 100;

                    double allocatedAmount =
                            (units / totalWeight) * totalCost;

                    result.add(
                            new ConsumptionDistributionDTO(
                                    household.getConsumerNumber(),
                                    units,
                                    percentage,
                                    allocatedAmount
                            )
                    );
                }

                /*
                 * Unmetered households
                 *
                 * Flat-area fallback.
                 */

                for (Household household :
                        unmeteredHouseholds) {

                    if (household.getFlatArea() == null ||
                            household.getFlatArea() <= 0) {

                        continue;
                    }

                    double flatArea =
                            household.getFlatArea();

                    double percentage =
                            (flatArea / totalWeight) * 100;

                    double allocatedAmount =
                            (flatArea / totalWeight) * totalCost;

                    result.add(
                            new ConsumptionDistributionDTO(
                                    household.getConsumerNumber(),
                                    0.0,
                                    percentage,
                                    allocatedAmount
                            )
                    );
                }
            }

        }

        /*
         * CASE 2:
         *
         * ALL households are unmetered.
         *
         * Therefore the complete cost is distributed
         * according to flat area.
         */
        else if (totalConsumption <= 0) {

            double totalFlatArea =
                    households.stream()
                            .filter(h -> h.getFlatArea() != null)
                            .mapToDouble(Household::getFlatArea)
                            .sum();

            if (totalFlatArea <= 0) {
                throw new RuntimeException(
                        "No valid meter consumption or flat area available");
            }

            for (Household household :
                    households) {

                if (household.getFlatArea() == null ||
                        household.getFlatArea() <= 0) {

                    continue;
                }

                double flatArea =
                        household.getFlatArea();

                double percentage =
                        (flatArea / totalFlatArea) * 100;

                double allocatedAmount =
                        (flatArea / totalFlatArea) * totalCost;

                result.add(
                        new ConsumptionDistributionDTO(
                                household.getConsumerNumber(),
                                0.0,
                                percentage,
                                allocatedAmount
                        )
                );
            }
        }

        /*
         * CASE 3:
         *
         * ALL households have meters.
         *
         * Use the original consumption-based calculation.
         */
        else {

            for (Household household :
                    meteredHouseholds) {

                MeterReading reading =
                        latestReadings.get(
                                household.getConsumerNumber());

                double units =
                        reading.getUnitsConsumed();

                double percentage =
                        (units / totalConsumption) * 100;

                double allocatedAmount =
                        (units / totalConsumption) * totalCost;

                result.add(
                        new ConsumptionDistributionDTO(
                                household.getConsumerNumber(),
                                units,
                                percentage,
                                allocatedAmount
                        )
                );
            }
        }

        // ---------------------------------------------------------
        // 8. Highest allocation/consumption first
        // ---------------------------------------------------------

        result.sort(
                Comparator.comparing(
                        ConsumptionDistributionDTO::getUnitsConsumed
                ).reversed()
        );

        return result;
    }
}