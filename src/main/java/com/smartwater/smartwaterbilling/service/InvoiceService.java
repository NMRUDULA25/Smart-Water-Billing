package com.smartwater.smartwaterbilling.service;

import com.smartwater.smartwaterbilling.dto.InvoiceDTO;

import java.util.List;

public interface InvoiceService {

    InvoiceDTO generateInvoice(Long waterBillId);

    InvoiceDTO getInvoiceById(Long id);

    InvoiceDTO getInvoiceByWaterBillId(Long waterBillId);

    List<InvoiceDTO> getAllInvoices();

    List<InvoiceDTO> getInvoicesByConsumerNumber(
            String consumerNumber
    );

    byte[] generateInvoicePdf(Long invoiceId);
}