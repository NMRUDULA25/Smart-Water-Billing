package com.smartwater.smartwaterbilling.controller;

import com.smartwater.smartwaterbilling.dto.BulkWaterPurchaseDTO;
import com.smartwater.smartwaterbilling.service.BulkWaterPurchaseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bulk-water-purchases")
@CrossOrigin(origins = "http://localhost:5173")
public class BulkWaterPurchaseController {

    private final BulkWaterPurchaseService service;

    public BulkWaterPurchaseController(
            BulkWaterPurchaseService service) {

        this.service = service;
    }

    @PostMapping
    public BulkWaterPurchaseDTO createBulkWaterPurchase(
            @RequestBody BulkWaterPurchaseDTO dto) {

        return service.createBulkWaterPurchase(dto);
    }

    @GetMapping
    public List<BulkWaterPurchaseDTO>
    getAllBulkWaterPurchases() {

        return service.getAllBulkWaterPurchases();
    }

    @GetMapping("/{id}")
    public BulkWaterPurchaseDTO
    getBulkWaterPurchaseById(
            @PathVariable Long id) {

        return service.getBulkWaterPurchaseById(id);
    }

    @PutMapping("/{id}")
    public BulkWaterPurchaseDTO
    updateBulkWaterPurchase(
            @PathVariable Long id,
            @RequestBody BulkWaterPurchaseDTO dto) {

        return service.updateBulkWaterPurchase(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteBulkWaterPurchase(
            @PathVariable Long id) {

        service.deleteBulkWaterPurchase(id);
    }

    @GetMapping("/supplier/{supplierName}")
    public List<BulkWaterPurchaseDTO>
    getPurchasesBySupplier(
            @PathVariable String supplierName) {

        return service.getPurchasesBySupplier(supplierName);
    }

    @GetMapping("/status/{status}")
    public List<BulkWaterPurchaseDTO>
    getPurchasesByStatus(
            @PathVariable String status) {

        return service.getPurchasesByStatus(status);
    }
}