package com.smartwater.smartwaterbilling.impl;

import com.smartwater.smartwaterbilling.dto.MeterReadingDTO;
import com.smartwater.smartwaterbilling.entity.MeterReading;
import com.smartwater.smartwaterbilling.mapper.MeterReadingMapper;
import com.smartwater.smartwaterbilling.repository.MeterReadingRepository;
import com.smartwater.smartwaterbilling.service.MeterReadingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeterReadingServiceImpl implements MeterReadingService {

    private final MeterReadingRepository meterReadingRepository;

    public MeterReadingServiceImpl(
            MeterReadingRepository meterReadingRepository) {

        this.meterReadingRepository = meterReadingRepository;
    }

    @Override
    public MeterReadingDTO createMeterReading(
            MeterReadingDTO meterReadingDTO) {

        validateReadings(
                meterReadingDTO.getPreviousReading(),
                meterReadingDTO.getCurrentReading()
        );

        double unitsConsumed =
                meterReadingDTO.getCurrentReading()
                        - meterReadingDTO.getPreviousReading();

        meterReadingDTO.setUnitsConsumed(unitsConsumed);

        MeterReading meterReading =
                MeterReadingMapper.toEntity(meterReadingDTO);

        MeterReading savedMeterReading =
                meterReadingRepository.save(meterReading);

        return MeterReadingMapper.toDTO(savedMeterReading);
    }

    @Override
    public MeterReadingDTO getMeterReadingById(Long id) {

        MeterReading meterReading =
                meterReadingRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Meter Reading not found"));

        return MeterReadingMapper.toDTO(meterReading);
    }

    @Override
    public List<MeterReadingDTO> getAllMeterReadings() {

        return meterReadingRepository.findAll()
                .stream()
                .map(MeterReadingMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MeterReadingDTO updateMeterReading(
            Long id,
            MeterReadingDTO meterReadingDTO) {

        MeterReading meterReading =
                meterReadingRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Meter Reading not found"));

        validateReadings(
                meterReadingDTO.getPreviousReading(),
                meterReadingDTO.getCurrentReading()
        );

        meterReading.setConsumerNumber(
                meterReadingDTO.getConsumerNumber());

        meterReading.setPreviousReading(
                meterReadingDTO.getPreviousReading());

        meterReading.setCurrentReading(
                meterReadingDTO.getCurrentReading());

        double unitsConsumed =
                meterReadingDTO.getCurrentReading()
                        - meterReadingDTO.getPreviousReading();

        meterReading.setUnitsConsumed(unitsConsumed);

        meterReading.setReadingDate(
                meterReadingDTO.getReadingDate());

        MeterReading updatedMeterReading =
                meterReadingRepository.save(meterReading);

        return MeterReadingMapper.toDTO(updatedMeterReading);
    }

    @Override
    public void deleteMeterReading(Long id) {

        if (!meterReadingRepository.existsById(id)) {
            throw new RuntimeException("Meter Reading not found");
        }

        meterReadingRepository.deleteById(id);
    }

    private void validateReadings(
            Double previousReading,
            Double currentReading) {

        if (previousReading == null || currentReading == null) {
            throw new RuntimeException(
                    "Previous and current readings are required");
        }

        if (previousReading < 0 || currentReading < 0) {
            throw new RuntimeException(
                    "Meter readings cannot be negative");
        }

        if (currentReading < previousReading) {
            throw new RuntimeException(
                    "Current reading cannot be less than previous reading");
        }
    }
}