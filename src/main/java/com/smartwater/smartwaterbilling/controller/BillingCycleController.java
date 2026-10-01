package com.smartwater.smartwaterbilling.controller;

import com.smartwater.smartwaterbilling.dto.BillingCycleDTO;
import com.smartwater.smartwaterbilling.service.BillingCycleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/billing-cycles")
@CrossOrigin(origins = "http://localhost:5173")
public class BillingCycleController {

    private final BillingCycleService billingCycleService;

    public BillingCycleController(BillingCycleService billingCycleService) {
        this.billingCycleService = billingCycleService;
    }

    // Create Billing Cycle
    @PostMapping
    public BillingCycleDTO createBillingCycle(
            @RequestBody BillingCycleDTO dto) {

        return billingCycleService.createBillingCycle(dto);
    }

    // Get All Billing Cycles
    @GetMapping
    public List<BillingCycleDTO> getAllBillingCycles() {

        return billingCycleService.getAllBillingCycles();
    }

    // Get Billing Cycle by ID
    @GetMapping("/{id}")
    public BillingCycleDTO getBillingCycleById(
            @PathVariable Long id) {

        return billingCycleService.getBillingCycleById(id);
    }

    // Update Billing Cycle
    @PutMapping("/{id}")
    public BillingCycleDTO updateBillingCycle(
            @PathVariable Long id,
            @RequestBody BillingCycleDTO dto) {

        return billingCycleService.updateBillingCycle(id, dto);
    }

    // Delete Billing Cycle
    @DeleteMapping("/{id}")
    public void deleteBillingCycle(
            @PathVariable Long id) {

        billingCycleService.deleteBillingCycle(id);
    }

    // Open Billing Cycle
    @PutMapping("/{id}/open")
    public BillingCycleDTO openBillingCycle(
            @PathVariable Long id) {

        return billingCycleService.openBillingCycle(id);
    }

    // Finalize Billing Cycle
    @PutMapping("/{id}/finalize")
    public BillingCycleDTO finalizeBillingCycle(
            @PathVariable Long id) {

        return billingCycleService.finalizeBillingCycle(id);
    }

    // Archive Billing Cycle
    @PutMapping("/{id}/archive")
    public BillingCycleDTO archiveBillingCycle(
            @PathVariable Long id) {

        return billingCycleService.archiveBillingCycle(id);
    }
}