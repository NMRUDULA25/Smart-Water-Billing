package com.smartwater.smartwaterbilling.controller;

import com.smartwater.smartwaterbilling.dto.MeterReadingDTO;
import com.smartwater.smartwaterbilling.service.MeterReadingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meter-readings")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class MeterReadingController {

    private final MeterReadingService meterReadingService;

    public MeterReadingController(
            MeterReadingService meterReadingService) {

        this.meterReadingService = meterReadingService;
    }

    @PostMapping
    public MeterReadingDTO createMeterReading(
            @RequestBody MeterReadingDTO meterReadingDTO) {

        return meterReadingService.createMeterReading(
                meterReadingDTO);
    }

    @GetMapping("/{id}")
    public MeterReadingDTO getMeterReadingById(
            @PathVariable Long id) {

        return meterReadingService.getMeterReadingById(id);
    }

    @GetMapping
    public List<MeterReadingDTO> getAllMeterReadings() {

        return meterReadingService.getAllMeterReadings();
    }

    @PutMapping("/{id}")
    public MeterReadingDTO updateMeterReading(
            @PathVariable Long id,
            @RequestBody MeterReadingDTO meterReadingDTO) {

        return meterReadingService.updateMeterReading(
                id,
                meterReadingDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteMeterReading(
            @PathVariable Long id) {

        meterReadingService.deleteMeterReading(id);
    }

    @GetMapping("/consumer/{consumerNumber}")
    public List<MeterReadingDTO> getReadingsByConsumer(
            @PathVariable String consumerNumber) {

        return meterReadingService
                .getAllMeterReadings()
                .stream()
                .filter(reading ->
                        reading.getConsumerNumber()
                                .equals(consumerNumber))
                .toList();
    }
}