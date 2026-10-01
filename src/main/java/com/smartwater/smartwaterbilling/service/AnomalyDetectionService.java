package com.smartwater.smartwaterbilling.service;

import com.smartwater.smartwaterbilling.dto.AnomalyDTO;

import java.util.List;

public interface AnomalyDetectionService {

    List<AnomalyDTO> detectAnomalies();
}