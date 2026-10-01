package com.smartwater.smartwaterbilling.impl;

import com.smartwater.smartwaterbilling.dto.PaymentDTO;
import com.smartwater.smartwaterbilling.entity.Payment;
import com.smartwater.smartwaterbilling.entity.WaterBill;
import com.smartwater.smartwaterbilling.mapper.PaymentMapper;
import com.smartwater.smartwaterbilling.repository.PaymentRepository;
import com.smartwater.smartwaterbilling.repository.WaterBillRepository;
import com.smartwater.smartwaterbilling.service.PaymentService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final WaterBillRepository waterBillRepository;

    public PaymentServiceImpl(
            PaymentRepository paymentRepository,
            WaterBillRepository waterBillRepository) {

        this.paymentRepository = paymentRepository;
        this.waterBillRepository = waterBillRepository;
    }

    // CREATE
    @Override
    public PaymentDTO createPayment(PaymentDTO dto) {

        if (dto.getBillId() == null) {
            throw new RuntimeException("Bill ID is required");
        }

        WaterBill waterBill =
                waterBillRepository.findById(dto.getBillId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Water Bill not found"
                                ));

        Payment payment =
                PaymentMapper.toEntity(dto, waterBill);

        Payment savedPayment =
                paymentRepository.save(payment);

        return PaymentMapper.toDTO(savedPayment);
    }

    // GET BY ID
    @Override
    public PaymentDTO getPaymentById(Long id) {

        Payment payment =
                paymentRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Payment not found"
                                ));

        return PaymentMapper.toDTO(payment);
    }

    // GET ALL
    @Override
    public List<PaymentDTO> getAllPayments() {

        return paymentRepository.findAll()
                .stream()
                .map(PaymentMapper::toDTO)
                .collect(Collectors.toList());
    }

    // UPDATE
    @Override
    public PaymentDTO updatePayment(
            Long id,
            PaymentDTO dto) {

        Payment payment =
                paymentRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Payment not found"
                                ));

        if (dto.getBillId() == null) {
            throw new RuntimeException(
                    "Bill ID is required"
            );
        }

        WaterBill waterBill =
                waterBillRepository.findById(dto.getBillId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Water Bill not found"
                                ));

        payment.setWaterBill(waterBill);

        payment.setConsumerNumber(
                dto.getConsumerNumber()
        );

        payment.setAmount(
                dto.getAmount()
        );

        payment.setPaymentDate(
                dto.getPaymentDate()
        );

        payment.setPaymentMethod(
                dto.getPaymentMethod()
        );

        payment.setStatus(
                dto.getStatus()
        );

        Payment updatedPayment =
                paymentRepository.save(payment);

        return PaymentMapper.toDTO(updatedPayment);
    }

    // DELETE
    @Override
    public void deletePayment(Long id) {

        Payment payment =
                paymentRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Payment not found"
                                ));

        paymentRepository.delete(payment);
    }
}