package com.smartwater.smartwaterbilling.impl;

import com.smartwater.smartwaterbilling.dto.AnomalyDTO;
import com.smartwater.smartwaterbilling.entity.MeterReading;
import com.smartwater.smartwaterbilling.repository.MeterReadingRepository;
import com.smartwater.smartwaterbilling.service.AnomalyDetectionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AnomalyDetectionServiceImpl
        implements AnomalyDetectionService {

    private final MeterReadingRepository meterReadingRepository;

    public AnomalyDetectionServiceImpl(
            MeterReadingRepository meterReadingRepository) {

        this.meterReadingRepository = meterReadingRepository;
    }

    @Override
    public List<AnomalyDTO> detectAnomalies() {

        List<MeterReading> readings =
                meterReadingRepository.findAll();

        if (readings.isEmpty()) {
            return new ArrayList<>();
        }

        // Get readings grouped by consumer
        Map<String, List<MeterReading>> groupedReadings =
                readings.stream()
                        .filter(r ->
                                r.getConsumerNumber() != null &&
                                        r.getUnitsConsumed() != null)
                        .collect(Collectors.groupingBy(
                                MeterReading::getConsumerNumber
                        ));

        List<AnomalyDTO> anomalies =
                new ArrayList<>();

        for (Map.Entry<String, List<MeterReading>> entry
                : groupedReadings.entrySet()) {

            String consumerNumber = entry.getKey();

            List<MeterReading> consumerReadings =
                    entry.getValue();

            // Sort using ID because readingDate is currently String
            consumerReadings.sort(
                    Comparator.comparing(
                            MeterReading::getId
                    ).reversed()
            );

            // Need at least two readings
            if (consumerReadings.size() < 2) {
                continue;
            }

            MeterReading latest =
                    consumerReadings.get(0);

            MeterReading previous =
                    consumerReadings.get(1);

            double currentConsumption =
                    latest.getUnitsConsumed();

            double previousConsumption =
                    previous.getUnitsConsumed();

            // Avoid division by zero
            double increasePercentage;

            if (previousConsumption == 0) {

                if (currentConsumption > 0) {
                    increasePercentage = 100.0;
                } else {
                    increasePercentage = 0.0;
                }

            } else {

                increasePercentage =
                        ((currentConsumption -
                                previousConsumption)
                                / previousConsumption) * 100;
            }

            String status;

            String message;

            /*
             * ANOMALY RULE:
             *
             * Current usage is more than
             * twice the previous usage.
             */

            if (currentConsumption >
                    previousConsumption * 2) {

                status = "ANOMALY";

                message =
                        "Water consumption has increased significantly.";

                anomalies.add(
                        new AnomalyDTO(
                                consumerNumber,
                                previousConsumption,
                                currentConsumption,
                                increasePercentage,
                                status,
                                message
                        )
                );

            }

        }

        return anomalies;
    }
}