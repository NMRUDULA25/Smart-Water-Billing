package com.smartwater.smartwaterbilling.controller;

import com.smartwater.smartwaterbilling.dto.InvoiceDTO;
import com.smartwater.smartwaterbilling.service.InvoiceService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@CrossOrigin(origins = {
        "http://localhost:8000",
        "http://localhost:5173"
})
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(
            InvoiceService invoiceService) {

        this.invoiceService = invoiceService;
    }

    @PostMapping("/generate/{waterBillId}")
    public InvoiceDTO generateInvoice(
            @PathVariable Long waterBillId) {

        return invoiceService.generateInvoice(
                waterBillId
        );
    }

    @GetMapping
    public List<InvoiceDTO> getAllInvoices() {

        return invoiceService.getAllInvoices();
    }

    @GetMapping("/{id}")
    public InvoiceDTO getInvoiceById(
            @PathVariable Long id) {

        return invoiceService.getInvoiceById(id);
    }

    @GetMapping("/consumer/{consumerNumber}")
    public List<InvoiceDTO> getInvoicesByConsumerNumber(
            @PathVariable String consumerNumber) {

        return invoiceService
                .getInvoicesByConsumerNumber(
                        consumerNumber
                );
    }
    @GetMapping("/{invoiceId}/pdf")
    public org.springframework.http.ResponseEntity<byte[]> downloadInvoicePdf(
            @PathVariable Long invoiceId) {

        byte[] pdf =
                invoiceService.generateInvoicePdf(invoiceId);

        return org.springframework.http.ResponseEntity.ok()
                .header(
                        org.springframework.http.HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=invoice-" + invoiceId + ".pdf"
                )
                .header(
                        org.springframework.http.HttpHeaders.CONTENT_TYPE,
                        "application/pdf"
                )
                .body(pdf);
    }
}