package com.smartwater.smartwaterbilling.controller;

import com.smartwater.smartwaterbilling.dto.TariffPlanDTO;
import com.smartwater.smartwaterbilling.service.TariffPlanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tariff-plans")
@CrossOrigin(origins = "http://localhost:3000")
public class TariffPlanController {

    private final TariffPlanService tariffPlanService;

    public TariffPlanController(TariffPlanService tariffPlanService) {
        this.tariffPlanService = tariffPlanService;
    }

    @PostMapping
    public TariffPlanDTO createTariffPlan(
            @RequestBody TariffPlanDTO dto) {

        return tariffPlanService.createTariffPlan(dto);
    }

    @GetMapping
    public List<TariffPlanDTO> getAllTariffPlans() {

        return tariffPlanService.getAllTariffPlans();
    }

    @GetMapping("/{id}")
    public TariffPlanDTO getTariffPlanById(
            @PathVariable Long id) {

        return tariffPlanService.getTariffPlanById(id);
    }

    @PutMapping("/{id}")
    public TariffPlanDTO updateTariffPlan(
            @PathVariable Long id,
            @RequestBody TariffPlanDTO dto) {

        return tariffPlanService.updateTariffPlan(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteTariffPlan(
            @PathVariable Long id) {

        tariffPlanService.deleteTariffPlan(id);
    }
}