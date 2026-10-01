package com.smartwater.smartwaterbilling.controller;

import com.smartwater.smartwaterbilling.dto.AnomalyDTO;
import com.smartwater.smartwaterbilling.service.AnomalyDetectionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/anomalies")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "http://localhost:5173"
})
public class AnomalyDetectionController {

    private final AnomalyDetectionService anomalyDetectionService;

    public AnomalyDetectionController(
            AnomalyDetectionService anomalyDetectionService) {

        this.anomalyDetectionService =
                anomalyDetectionService;
    }

    @GetMapping
    public List<AnomalyDTO> detectAnomalies() {

        return anomalyDetectionService.detectAnomalies();
    }
}