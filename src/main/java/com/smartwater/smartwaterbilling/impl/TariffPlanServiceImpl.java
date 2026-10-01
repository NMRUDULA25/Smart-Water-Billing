package com.smartwater.smartwaterbilling.impl;

import com.smartwater.smartwaterbilling.dto.TariffPlanDTO;
import com.smartwater.smartwaterbilling.entity.TariffPlan;
import com.smartwater.smartwaterbilling.mapper.TariffPlanMapper;
import com.smartwater.smartwaterbilling.repository.TariffPlanRepository;
import com.smartwater.smartwaterbilling.service.TariffPlanService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TariffPlanServiceImpl implements TariffPlanService {

    private final TariffPlanRepository tariffPlanRepository;

    public TariffPlanServiceImpl(TariffPlanRepository tariffPlanRepository) {
        this.tariffPlanRepository = tariffPlanRepository;
    }

    @Override
    public TariffPlanDTO createTariffPlan(TariffPlanDTO dto) {

        TariffPlan tariffPlan = TariffPlanMapper.toEntity(dto);

        TariffPlan saved = tariffPlanRepository.save(tariffPlan);

        return TariffPlanMapper.toDTO(saved);
    }

    @Override
    public TariffPlanDTO getTariffPlanById(Long id) {

        TariffPlan tariffPlan = tariffPlanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tariff Plan not found"));

        return TariffPlanMapper.toDTO(tariffPlan);
    }

    @Override
    public List<TariffPlanDTO> getAllTariffPlans() {

        return tariffPlanRepository.findAll()
                .stream()
                .map(TariffPlanMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TariffPlanDTO updateTariffPlan(Long id, TariffPlanDTO dto) {

        TariffPlan tariffPlan = tariffPlanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tariff Plan not found"));

        tariffPlan.setPlanName(dto.getPlanName());
        tariffPlan.setBaseRate(dto.getBaseRate());
        tariffPlan.setHigherRate(dto.getHigherRate());
        tariffPlan.setBaseLimit(dto.getBaseLimit());

        TariffPlan updated = tariffPlanRepository.save(tariffPlan);

        return TariffPlanMapper.toDTO(updated);
    }

    @Override
    public void deleteTariffPlan(Long id) {

        TariffPlan tariffPlan = tariffPlanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tariff Plan not found"));

        tariffPlanRepository.delete(tariffPlan);
    }
}