package com.smartwater.smartwaterbilling.controller;

import com.smartwater.smartwaterbilling.dto.PaymentDTO;
import com.smartwater.smartwaterbilling.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public PaymentDTO createPayment(@RequestBody PaymentDTO dto) {
        return paymentService.createPayment(dto);
    }

    @GetMapping("/{id}")
    public PaymentDTO getPaymentById(@PathVariable Long id) {
        return paymentService.getPaymentById(id);
    }

    @GetMapping
    public List<PaymentDTO> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @PutMapping("/{id}")
    public PaymentDTO updatePayment(
            @PathVariable Long id,
            @RequestBody PaymentDTO dto) {

        return paymentService.updatePayment(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
    }
}