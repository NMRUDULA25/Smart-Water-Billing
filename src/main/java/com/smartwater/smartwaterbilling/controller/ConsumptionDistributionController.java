package com.smartwater.smartwaterbilling.controller;

import com.smartwater.smartwaterbilling.dto.ConsumptionDistributionDTO;
import com.smartwater.smartwaterbilling.service.ConsumptionDistributionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consumption-distribution")
@CrossOrigin(origins = "http://localhost:5173")
public class ConsumptionDistributionController {

    private final ConsumptionDistributionService distributionService;

    public ConsumptionDistributionController(
            ConsumptionDistributionService distributionService) {

        this.distributionService = distributionService;
    }

    @GetMapping("/{purchaseId}")
    public List<ConsumptionDistributionDTO> distributeCost(
            @PathVariable Long purchaseId) {

        return distributionService.distributeCost(purchaseId);
    }
}