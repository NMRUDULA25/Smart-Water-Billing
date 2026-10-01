package com.smartwater.smartwaterbilling.impl;

import com.smartwater.smartwaterbilling.dto.WaterBillDTO;
import com.smartwater.smartwaterbilling.entity.TariffPlan;
import com.smartwater.smartwaterbilling.entity.WaterBill;
import com.smartwater.smartwaterbilling.mapper.WaterBillMapper;
import com.smartwater.smartwaterbilling.repository.TariffPlanRepository;
import com.smartwater.smartwaterbilling.repository.WaterBillRepository;
import com.smartwater.smartwaterbilling.service.TariffCalculationService;
import com.smartwater.smartwaterbilling.service.WaterBillService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WaterBillServiceImpl implements WaterBillService {

    private final WaterBillRepository waterBillRepository;
    private final TariffCalculationService tariffCalculationService;
    private final TariffPlanRepository tariffPlanRepository;

    public WaterBillServiceImpl(
            WaterBillRepository waterBillRepository,
            TariffCalculationService tariffCalculationService,
            TariffPlanRepository tariffPlanRepository) {

        this.waterBillRepository = waterBillRepository;
        this.tariffCalculationService = tariffCalculationService;
        this.tariffPlanRepository = tariffPlanRepository;
    }

    // CREATE
    @Override
    public WaterBillDTO createWaterBill(WaterBillDTO dto) {

        if (dto.getTariffPlanId() == null) {
            throw new RuntimeException("Tariff Plan ID is required");
        }

        TariffPlan tariffPlan =
                tariffPlanRepository.findById(dto.getTariffPlanId())
                        .orElseThrow(() ->
                                new RuntimeException("Tariff Plan not found"));

        if (dto.getUnitsConsumed() == null ||
                dto.getUnitsConsumed() < 0) {

            throw new RuntimeException(
                    "Units consumed must be greater than or equal to 0");
        }

        Double totalAmount =
                tariffCalculationService.calculateBill(
                        dto.getUnitsConsumed(),
                        tariffPlan
                );

        dto.setRatePerUnit(tariffPlan.getBaseRate());
        dto.setTotalAmount(totalAmount);

        if (dto.getStatus() == null ||
                dto.getStatus().isBlank()) {

            dto.setStatus("UNPAID");
        }

        WaterBill waterBill =
                WaterBillMapper.toEntity(dto, tariffPlan);

        WaterBill savedBill =
                waterBillRepository.save(waterBill);

        return WaterBillMapper.toDTO(savedBill);
    }

    // GET BY ID
    @Override
    public WaterBillDTO getWaterBillById(Long id) {

        WaterBill bill =
                waterBillRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Water Bill not found"));

        return WaterBillMapper.toDTO(bill);
    }

    // GET ALL
    @Override
    public List<WaterBillDTO> getAllWaterBills() {

        return waterBillRepository.findAll()
                .stream()
                .map(WaterBillMapper::toDTO)
                .collect(Collectors.toList());
    }

    // UPDATE
    @Override
    public WaterBillDTO updateWaterBill(
            Long id,
            WaterBillDTO dto) {

        WaterBill bill =
                waterBillRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Water Bill not found"));

        if (dto.getTariffPlanId() == null) {
            throw new RuntimeException(
                    "Tariff Plan ID is required");
        }

        TariffPlan tariffPlan =
                tariffPlanRepository.findById(dto.getTariffPlanId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Tariff Plan not found"));

        if (dto.getUnitsConsumed() == null ||
                dto.getUnitsConsumed() < 0) {

            throw new RuntimeException(
                    "Units consumed must be greater than or equal to 0");
        }

        bill.setConsumerNumber(
                dto.getConsumerNumber()
        );

        bill.setUnitsConsumed(
                dto.getUnitsConsumed()
        );

        bill.setRatePerUnit(
                tariffPlan.getBaseRate()
        );

        bill.setTotalAmount(
                tariffCalculationService.calculateBill(
                        dto.getUnitsConsumed(),
                        tariffPlan
                )
        );

        bill.setBillDate(
                dto.getBillDate()
        );

        bill.setBillingCycle(
                dto.getBillingCycle()
        );

        if (dto.getStatus() != null &&
                !dto.getStatus().isBlank()) {

            bill.setStatus(dto.getStatus());

        } else {

            bill.setStatus("UNPAID");
        }

        bill.setTariffPlan(tariffPlan);

        WaterBill updatedBill =
                waterBillRepository.save(bill);

        return WaterBillMapper.toDTO(updatedBill);
    }

    // DELETE
    @Override
    public void deleteWaterBill(Long id) {

        WaterBill bill =
                waterBillRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Water Bill not found"));

        waterBillRepository.delete(bill);
    }

    // GET BY CONSUMER
    @Override
    public List<WaterBillDTO> getBillsByConsumerNumber(
            String consumerNumber) {

        return waterBillRepository
                .findByConsumerNumber(consumerNumber)
                .stream()
                .map(WaterBillMapper::toDTO)
                .collect(Collectors.toList());
    }

    // GET BY BILLING CYCLE
    @Override
    public List<WaterBillDTO> getBillsByBillingCycle(
            String billingCycle) {

        return waterBillRepository
                .findByBillingCycle(billingCycle)
                .stream()
                .map(WaterBillMapper::toDTO)
                .collect(Collectors.toList());
    }

    // GET BY STATUS
    @Override
    public List<WaterBillDTO> getBillsByStatus(
            String status) {

        return waterBillRepository
                .findByStatus(status)
                .stream()
                .map(WaterBillMapper::toDTO)
                .collect(Collectors.toList());
    }

    // MARK AS PAID
    @Override
    public WaterBillDTO markBillAsPaid(Long id) {

        WaterBill bill =
                waterBillRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Water Bill not found"));

        bill.setStatus("PAID");

        WaterBill updatedBill =
                waterBillRepository.save(bill);

        return WaterBillMapper.toDTO(updatedBill);
    }
}