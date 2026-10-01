package com.smartwater.smartwaterbilling.service;

import com.smartwater.smartwaterbilling.dto.MeterReadingDTO;

import java.util.List;

public interface MeterReadingService {

    MeterReadingDTO createMeterReading(MeterReadingDTO meterReadingDTO);

    MeterReadingDTO getMeterReadingById(Long id);

    List<MeterReadingDTO> getAllMeterReadings();

    MeterReadingDTO updateMeterReading(Long id, MeterReadingDTO meterReadingDTO);

    void deleteMeterReading(Long id);
}