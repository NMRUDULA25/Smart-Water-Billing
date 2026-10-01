package com.smartwater.smartwaterbilling.mapper;

import com.smartwater.smartwaterbilling.dto.InvoiceDTO;
import com.smartwater.smartwaterbilling.entity.Invoice;
import org.springframework.stereotype.Component;

@Component
public class InvoiceMapper {

    public InvoiceDTO toDTO(Invoice entity) {

        if (entity == null) {
            return null;
        }

        InvoiceDTO dto = new InvoiceDTO();

        dto.setId(entity.getId());

        dto.setWaterBillId(
                entity.getWaterBillId()
        );

        dto.setConsumerNumber(
                entity.getConsumerNumber()
        );

        dto.setUnitsConsumed(
                entity.getUnitsConsumed()
        );

        dto.setRatePerUnit(
                entity.getRatePerUnit()
        );

        dto.setTotalAmount(
                entity.getTotalAmount()
        );

        dto.setBillDate(
                entity.getInvoiceDate()
        );

        dto.setBillingCycle(
                entity.getBillingCycle()
        );

        dto.setStatus(
                entity.getStatus()
        );

        return dto;
    }

    public Invoice toEntity(InvoiceDTO dto) {

        if (dto == null) {
            return null;
        }

        Invoice entity = new Invoice();

        entity.setId(dto.getId());

        entity.setWaterBillId(
                dto.getWaterBillId()
        );

        entity.setConsumerNumber(
                dto.getConsumerNumber()
        );

        entity.setUnitsConsumed(
                dto.getUnitsConsumed()
        );

        entity.setRatePerUnit(
                dto.getRatePerUnit()
        );

        entity.setTotalAmount(
                dto.getTotalAmount()
        );

        entity.setInvoiceDate(
                dto.getBillDate()
        );

        entity.setBillingCycle(
                dto.getBillingCycle()
        );

        entity.setStatus(
                dto.getStatus()
        );

        return entity;
    }
}