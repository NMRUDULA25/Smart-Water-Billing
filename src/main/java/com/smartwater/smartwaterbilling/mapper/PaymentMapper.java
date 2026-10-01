package com.smartwater.smartwaterbilling.mapper;

import com.smartwater.smartwaterbilling.dto.PaymentDTO;
import com.smartwater.smartwaterbilling.entity.Payment;
import com.smartwater.smartwaterbilling.entity.WaterBill;

public class PaymentMapper {

    // Entity -> DTO
    public static PaymentDTO toDTO(Payment payment) {

        if (payment == null) {
            return null;
        }

        Long billId = null;

        if (payment.getWaterBill() != null) {
            billId = payment.getWaterBill().getId();
        }

        return new PaymentDTO(
                payment.getId(),
                billId,
                payment.getConsumerNumber(),
                payment.getAmount(),
                payment.getPaymentDate(),
                payment.getPaymentMethod(),
                payment.getStatus()
        );
    }

    // DTO -> Entity
    public static Payment toEntity(
            PaymentDTO dto,
            WaterBill waterBill) {

        if (dto == null) {
            return null;
        }

        Payment payment = new Payment();

        payment.setId(dto.getId());
        payment.setWaterBill(waterBill);
        payment.setConsumerNumber(dto.getConsumerNumber());
        payment.setAmount(dto.getAmount());
        payment.setPaymentDate(dto.getPaymentDate());
        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setStatus(dto.getStatus());

        return payment;
    }
}