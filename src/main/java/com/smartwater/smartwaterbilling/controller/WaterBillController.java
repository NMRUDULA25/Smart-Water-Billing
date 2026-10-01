package com.smartwater.smartwaterbilling.controller;

import com.smartwater.smartwaterbilling.dto.WaterBillDTO;
import com.smartwater.smartwaterbilling.service.WaterBillService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/water-bills")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "http://localhost:5173"
})
public class WaterBillController {

    private final WaterBillService waterBillService;

    public WaterBillController(
            WaterBillService waterBillService) {

        this.waterBillService = waterBillService;
    }

    // CREATE
    @PostMapping
    public WaterBillDTO createWaterBill(
            @RequestBody WaterBillDTO dto) {

        return waterBillService.createWaterBill(dto);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public WaterBillDTO getWaterBillById(
            @PathVariable Long id) {

        return waterBillService.getWaterBillById(id);
    }

    // GET ALL
    @GetMapping
    public List<WaterBillDTO> getAllWaterBills() {

        return waterBillService.getAllWaterBills();
    }

    // UPDATE
    @PutMapping("/{id}")
    public WaterBillDTO updateWaterBill(
            @PathVariable Long id,
            @RequestBody WaterBillDTO dto) {

        return waterBillService.updateWaterBill(id, dto);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteWaterBill(
            @PathVariable Long id) {

        waterBillService.deleteWaterBill(id);
    }

    // GET BY CONSUMER
    @GetMapping("/consumer/{consumerNumber}")
    public List<WaterBillDTO> getBillsByConsumerNumber(
            @PathVariable String consumerNumber) {

        return waterBillService
                .getBillsByConsumerNumber(consumerNumber);
    }

    // GET BY BILLING CYCLE
    @GetMapping("/cycle/{billingCycle}")
    public List<WaterBillDTO> getBillsByBillingCycle(
            @PathVariable String billingCycle) {

        return waterBillService
                .getBillsByBillingCycle(billingCycle);
    }

    // GET BY STATUS
    @GetMapping("/status/{status}")
    public List<WaterBillDTO> getBillsByStatus(
            @PathVariable String status) {

        return waterBillService
                .getBillsByStatus(status);
    }

    // MARK AS PAID
    @PutMapping("/{id}/pay")
    public WaterBillDTO markBillAsPaid(
            @PathVariable Long id) {

        return waterBillService.markBillAsPaid(id);
    }
}