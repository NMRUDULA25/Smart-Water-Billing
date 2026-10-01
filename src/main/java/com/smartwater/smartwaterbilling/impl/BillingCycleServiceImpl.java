package com.smartwater.smartwaterbilling.impl;

import com.smartwater.smartwaterbilling.dto.BillingCycleDTO;
import com.smartwater.smartwaterbilling.entity.BillingCycle;
import com.smartwater.smartwaterbilling.mapper.BillingCycleMapper;
import com.smartwater.smartwaterbilling.repository.BillingCycleRepository;
import com.smartwater.smartwaterbilling.service.BillingCycleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillingCycleServiceImpl implements BillingCycleService {

    private final BillingCycleRepository billingCycleRepository;

    public BillingCycleServiceImpl(BillingCycleRepository billingCycleRepository) {
        this.billingCycleRepository = billingCycleRepository;
    }

    // Create Billing Cycle
    @Override
    public BillingCycleDTO createBillingCycle(BillingCycleDTO dto) {

        BillingCycle billingCycle = BillingCycleMapper.toEntity(dto);

        // Default status
        if (billingCycle.getStatus() == null || billingCycle.getStatus().isBlank()) {
            billingCycle.setStatus("OPEN");
        }

        BillingCycle saved = billingCycleRepository.save(billingCycle);

        return BillingCycleMapper.toDTO(saved);
    }

    // Get Billing Cycle by ID
    @Override
    public BillingCycleDTO getBillingCycleById(Long id) {

        BillingCycle billingCycle = billingCycleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Billing Cycle Not Found"));

        return BillingCycleMapper.toDTO(billingCycle);
    }

    // Get All Billing Cycles
    @Override
    public List<BillingCycleDTO> getAllBillingCycles() {

        return billingCycleRepository.findAll()
                .stream()
                .map(BillingCycleMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Update Billing Cycle
    @Override
    public BillingCycleDTO updateBillingCycle(Long id, BillingCycleDTO dto) {

        BillingCycle billingCycle = billingCycleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Billing Cycle Not Found"));

        billingCycle.setCycleName(dto.getCycleName());
        billingCycle.setStartDate(dto.getStartDate());
        billingCycle.setEndDate(dto.getEndDate());
        billingCycle.setStatus(dto.getStatus());

        BillingCycle updated = billingCycleRepository.save(billingCycle);

        return BillingCycleMapper.toDTO(updated);
    }

    // Delete Billing Cycle
    @Override
    public void deleteBillingCycle(Long id) {

        BillingCycle billingCycle = billingCycleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Billing Cycle Not Found"));

        billingCycleRepository.delete(billingCycle);
    }

    // Open Billing Cycle
    @Override
    public BillingCycleDTO openBillingCycle(Long id) {

        BillingCycle billingCycle = billingCycleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Billing Cycle Not Found"));

        billingCycle.setStatus("OPEN");

        BillingCycle updated = billingCycleRepository.save(billingCycle);

        return BillingCycleMapper.toDTO(updated);
    }

    // Finalize Billing Cycle
    @Override
    public BillingCycleDTO finalizeBillingCycle(Long id) {

        BillingCycle billingCycle = billingCycleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Billing Cycle Not Found"));

        billingCycle.setStatus("FINALIZED");

        BillingCycle updated = billingCycleRepository.save(billingCycle);

        return BillingCycleMapper.toDTO(updated);
    }

    // Archive Billing Cycle
    @Override
    public BillingCycleDTO archiveBillingCycle(Long id) {

        BillingCycle billingCycle = billingCycleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Billing Cycle Not Found"));

        billingCycle.setStatus("ARCHIVED");

        BillingCycle updated = billingCycleRepository.save(billingCycle);

        return BillingCycleMapper.toDTO(updated);
    }
}