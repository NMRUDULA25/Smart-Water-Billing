package com.smartwater.smartwaterbilling.impl;

import com.smartwater.smartwaterbilling.dto.BulkWaterPurchaseDTO;
import com.smartwater.smartwaterbilling.entity.BulkWaterPurchase;
import com.smartwater.smartwaterbilling.mapper.BulkWaterPurchaseMapper;
import com.smartwater.smartwaterbilling.repository.BulkWaterPurchaseRepository;
import com.smartwater.smartwaterbilling.service.BulkWaterPurchaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BulkWaterPurchaseServiceImpl
        implements BulkWaterPurchaseService {

    private final BulkWaterPurchaseRepository repository;

    public BulkWaterPurchaseServiceImpl(
            BulkWaterPurchaseRepository repository) {

        this.repository = repository;
    }

    @Override
    public BulkWaterPurchaseDTO createBulkWaterPurchase(
            BulkWaterPurchaseDTO dto) {

        if (dto.getQuantity() == null ||
                dto.getPricePerUnit() == null) {

            throw new RuntimeException(
                    "Quantity and price per unit are required");
        }

        BulkWaterPurchase purchase =
                BulkWaterPurchaseMapper.toEntity(dto);

        Double totalAmount =
                dto.getQuantity() * dto.getPricePerUnit();

        purchase.setTotalAmount(totalAmount);

        if (purchase.getStatus() == null ||
                purchase.getStatus().isBlank()) {

            purchase.setStatus("ACTIVE");
        }

        BulkWaterPurchase saved =
                repository.save(purchase);

        return BulkWaterPurchaseMapper.toDTO(saved);
    }

    @Override
    public List<BulkWaterPurchaseDTO>
    getAllBulkWaterPurchases() {

        return repository.findAll()
                .stream()
                .map(BulkWaterPurchaseMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BulkWaterPurchaseDTO
    getBulkWaterPurchaseById(Long id) {

        BulkWaterPurchase purchase =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Bulk water purchase not found with id: "
                                                + id));

        return BulkWaterPurchaseMapper.toDTO(purchase);
    }

    @Override
    public BulkWaterPurchaseDTO
    updateBulkWaterPurchase(
            Long id,
            BulkWaterPurchaseDTO dto) {

        BulkWaterPurchase purchase =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Bulk water purchase not found with id: "
                                                + id));

        if (dto.getQuantity() == null ||
                dto.getPricePerUnit() == null) {

            throw new RuntimeException(
                    "Quantity and price per unit are required");
        }

        purchase.setSupplierName(
                dto.getSupplierName());

        purchase.setQuantity(
                dto.getQuantity());

        purchase.setPricePerUnit(
                dto.getPricePerUnit());

        purchase.setPurchaseDate(
                dto.getPurchaseDate());

        if (dto.getStatus() != null &&
                !dto.getStatus().isBlank()) {

            purchase.setStatus(dto.getStatus());
        }

        Double totalAmount =
                dto.getQuantity() *
                        dto.getPricePerUnit();

        purchase.setTotalAmount(totalAmount);

        BulkWaterPurchase updated =
                repository.save(purchase);

        return BulkWaterPurchaseMapper.toDTO(updated);
    }

    @Override
    public void deleteBulkWaterPurchase(Long id) {

        if (!repository.existsById(id)) {

            throw new RuntimeException(
                    "Bulk water purchase not found with id: "
                            + id);
        }

        repository.deleteById(id);
    }

    @Override
    public List<BulkWaterPurchaseDTO>
    getPurchasesBySupplier(String supplierName) {

        return repository
                .findBySupplierNameIgnoreCase(supplierName)
                .stream()
                .map(BulkWaterPurchaseMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BulkWaterPurchaseDTO>
    getPurchasesByStatus(String status) {

        return repository
                .findByStatusIgnoreCase(status)
                .stream()
                .map(BulkWaterPurchaseMapper::toDTO)
                .collect(Collectors.toList());
    }
}