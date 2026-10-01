package com.smartwater.smartwaterbilling.mapper;

import com.smartwater.smartwaterbilling.dto.MeterReadingDTO;
import com.smartwater.smartwaterbilling.entity.MeterReading;

public class MeterReadingMapper {

    private MeterReadingMapper() {
        // Utility class
    }

    public static MeterReadingDTO toDTO(MeterReading meterReading) {

        if (meterReading == null) {
            return null;
        }

        return new MeterReadingDTO(
                meterReading.getId(),
                meterReading.getConsumerNumber(),
                meterReading.getPreviousReading(),
                meterReading.getCurrentReading(),
                meterReading.getUnitsConsumed(),
                meterReading.getReadingDate()
        );
    }

    public static MeterReading toEntity(MeterReadingDTO dto) {

        if (dto == null) {
            return null;
        }

        MeterReading meterReading = new MeterReading();

        meterReading.setId(dto.getId());
        meterReading.setConsumerNumber(dto.getConsumerNumber());
        meterReading.setPreviousReading(dto.getPreviousReading());
        meterReading.setCurrentReading(dto.getCurrentReading());

        // unitsConsumed is calculated in the service
        meterReading.setUnitsConsumed(dto.getUnitsConsumed());

        meterReading.setReadingDate(dto.getReadingDate());

        return meterReading;
    }
}