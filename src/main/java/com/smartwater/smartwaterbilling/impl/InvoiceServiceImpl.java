package com.smartwater.smartwaterbilling.impl;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import com.smartwater.smartwaterbilling.dto.InvoiceDTO;
import com.smartwater.smartwaterbilling.entity.Invoice;
import com.smartwater.smartwaterbilling.entity.WaterBill;
import com.smartwater.smartwaterbilling.mapper.InvoiceMapper;
import com.smartwater.smartwaterbilling.repository.InvoiceRepository;
import com.smartwater.smartwaterbilling.repository.WaterBillRepository;
import com.smartwater.smartwaterbilling.service.InvoiceService;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final WaterBillRepository waterBillRepository;
    private final InvoiceMapper invoiceMapper;

    public InvoiceServiceImpl(
            InvoiceRepository invoiceRepository,
            WaterBillRepository waterBillRepository,
            InvoiceMapper invoiceMapper) {

        this.invoiceRepository = invoiceRepository;
        this.waterBillRepository = waterBillRepository;
        this.invoiceMapper = invoiceMapper;
    }

    // =========================================================
    // GENERATE INVOICE FROM WATER BILL
    // =========================================================

    @Override
    public InvoiceDTO generateInvoice(Long waterBillId) {

        WaterBill bill = waterBillRepository.findById(waterBillId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Water Bill not found with id: "
                                        + waterBillId
                        )
                );

        // Check if invoice already exists
        Invoice existingInvoice =
                invoiceRepository.findByWaterBillId(waterBillId)
                        .orElse(null);

        if (existingInvoice != null) {
            return invoiceMapper.toDTO(existingInvoice);
        }

        // Create new invoice
        Invoice invoice = new Invoice();

        invoice.setWaterBillId(bill.getId());

        invoice.setConsumerNumber(
                bill.getConsumerNumber()
        );

        invoice.setBillingCycle(
                bill.getBillingCycle()
        );

        invoice.setUnitsConsumed(
                bill.getUnitsConsumed()
        );

        invoice.setRatePerUnit(
                bill.getRatePerUnit()
        );

        /*
         * At present the WaterBill contains the final
         * calculated amount.
         *
         * Therefore we use it as the base charge.
         */
        invoice.setBaseCharge(
                bill.getTotalAmount()
        );

        /*
         * These values can be populated later when
         * shared-area allocation and adjustments
         * are available separately.
         */
        invoice.setSharedCostAllocation(0.0);

        invoice.setAdjustment(0.0);

        invoice.setTotalAmount(
                bill.getTotalAmount()
        );

        invoice.setInvoiceDate(
                bill.getBillDate()
        );

        invoice.setStatus(
                bill.getStatus()
        );

        // SAVE INVOICE
        Invoice savedInvoice =
                invoiceRepository.save(invoice);

        return invoiceMapper.toDTO(savedInvoice);
    }


    // =========================================================
    // GET INVOICE BY INVOICE ID
    // =========================================================

    @Override
    public InvoiceDTO getInvoiceById(Long id) {

        Invoice invoice =
                invoiceRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Invoice not found with id: "
                                                + id
                                )
                        );

        return invoiceMapper.toDTO(invoice);
    }


    // =========================================================
    // GET INVOICE BY WATER BILL ID
    // =========================================================

    @Override
    public InvoiceDTO getInvoiceByWaterBillId(
            Long waterBillId) {

        Invoice invoice =
                invoiceRepository
                        .findByWaterBillId(waterBillId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Invoice not found for water bill id: "
                                                + waterBillId
                                )
                        );

        return invoiceMapper.toDTO(invoice);
    }


    // =========================================================
    // GET ALL INVOICES
    // =========================================================

    @Override
    public List<InvoiceDTO> getAllInvoices() {

        return invoiceRepository.findAll()
                .stream()
                .map(invoiceMapper::toDTO)
                .collect(Collectors.toList());
    }


    // =========================================================
    // GET INVOICES BY CONSUMER
    // =========================================================

    @Override
    public List<InvoiceDTO> getInvoicesByConsumerNumber(
            String consumerNumber) {

        return invoiceRepository
                .findByConsumerNumber(consumerNumber)
                .stream()
                .map(invoiceMapper::toDTO)
                .collect(Collectors.toList());
    }


    // =========================================================
    // GENERATE PDF INVOICE
    // =========================================================

    @Override
    public byte[] generateInvoicePdf(Long invoiceId) {

        Invoice invoice =
                invoiceRepository.findById(invoiceId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Invoice not found with id: "
                                                + invoiceId
                                )
                        );

        try {

            ByteArrayOutputStream outputStream =
                    new ByteArrayOutputStream();

            Document document = new Document();

            PdfWriter.getInstance(
                    document,
                    outputStream
            );

            document.open();

            // -------------------------------------------------
            // TITLE
            // -------------------------------------------------

            Font titleFont =
                    FontFactory.getFont(
                            FontFactory.HELVETICA_BOLD,
                            18
                    );

            Paragraph title =
                    new Paragraph(
                            "SMART WATER BILLING SYSTEM",
                            titleFont
                    );

            title.setAlignment(
                    Paragraph.ALIGN_CENTER
            );

            document.add(title);

            document.add(
                    new Paragraph(" ")
            );

            Font headingFont =
                    FontFactory.getFont(
                            FontFactory.HELVETICA_BOLD,
                            14
                    );

            Paragraph invoiceHeading =
                    new Paragraph(
                            "WATER BILL INVOICE",
                            headingFont
                    );

            invoiceHeading.setAlignment(
                    Paragraph.ALIGN_CENTER
            );

            document.add(invoiceHeading);

            document.add(
                    new Paragraph(" ")
            );


            // -------------------------------------------------
            // INVOICE DETAILS
            // -------------------------------------------------

            PdfPTable table =
                    new PdfPTable(2);

            table.setWidthPercentage(100);

            table.addCell(
                    new Phrase("Invoice ID")
            );

            table.addCell(
                    new Phrase(
                            String.valueOf(
                                    invoice.getId()
                            )
                    )
            );

            table.addCell(
                    new Phrase("Consumer Number")
            );

            table.addCell(
                    new Phrase(
                            invoice.getConsumerNumber()
                    )
            );

            table.addCell(
                    new Phrase("Billing Cycle")
            );

            table.addCell(
                    new Phrase(
                            invoice.getBillingCycle()
                    )
            );

            table.addCell(
                    new Phrase("Units Consumed")
            );

            table.addCell(
                    new Phrase(
                            String.valueOf(
                                    invoice.getUnitsConsumed()
                            )
                    )
            );

            table.addCell(
                    new Phrase("Rate Per Unit")
            );

            table.addCell(
                    new Phrase(
                            "₹ "
                                    + invoice.getRatePerUnit()
                    )
            );

            table.addCell(
                    new Phrase("Base Charge")
            );

            table.addCell(
                    new Phrase(
                            "₹ "
                                    + invoice.getBaseCharge()
                    )
            );

            table.addCell(
                    new Phrase("Shared Cost Allocation")
            );

            table.addCell(
                    new Phrase(
                            "₹ "
                                    + invoice.getSharedCostAllocation()
                    )
            );

            table.addCell(
                    new Phrase("Adjustment")
            );

            table.addCell(
                    new Phrase(
                            "₹ "
                                    + invoice.getAdjustment()
                    )
            );

            table.addCell(
                    new Phrase("Total Amount")
            );

            table.addCell(
                    new Phrase(
                            "₹ "
                                    + invoice.getTotalAmount()
                    )
            );

            table.addCell(
                    new Phrase("Invoice Date")
            );

            table.addCell(
                    new Phrase(
                            String.valueOf(
                                    invoice.getInvoiceDate()
                            )
                    )
            );

            table.addCell(
                    new Phrase("Status")
            );

            table.addCell(
                    new Phrase(
                            invoice.getStatus()
                    )
            );

            document.add(table);

            document.add(
                    new Paragraph(" ")
            );

            // -------------------------------------------------
            // FOOTER
            // -------------------------------------------------

            document.add(
                    new Paragraph(
                            "Thank you for using Smart Water Billing System."
                    )
            );

            document.close();

            return outputStream.toByteArray();

        } catch (DocumentException e) {

            throw new RuntimeException(
                    "Error generating invoice PDF",
                    e
            );
        }
    }
}