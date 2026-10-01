package com.smartwater.smartwaterbilling.impl;

import com.smartwater.smartwaterbilling.dto.DashboardReportDTO;
import com.smartwater.smartwaterbilling.repository.ConsumerRepository;
import com.smartwater.smartwaterbilling.repository.MeterReadingRepository;
import com.smartwater.smartwaterbilling.repository.PaymentRepository;
import com.smartwater.smartwaterbilling.repository.WaterBillRepository;
import com.smartwater.smartwaterbilling.service.ReportService;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    private final ConsumerRepository consumerRepository;
    private final MeterReadingRepository meterReadingRepository;
    private final WaterBillRepository waterBillRepository;
    private final PaymentRepository paymentRepository;

    public ReportServiceImpl(
            ConsumerRepository consumerRepository,
            MeterReadingRepository meterReadingRepository,
            WaterBillRepository waterBillRepository,
            PaymentRepository paymentRepository) {

        this.consumerRepository = consumerRepository;
        this.meterReadingRepository = meterReadingRepository;
        this.waterBillRepository = waterBillRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public DashboardReportDTO getDashboardReport() {

        Long totalConsumers = consumerRepository.count();

        Long totalMeterReadings = meterReadingRepository.count();

        Long totalBills = waterBillRepository.count();

        Long paidBills = waterBillRepository.countByStatus("PAID");

        Long unpaidBills = waterBillRepository.countByStatus("UNPAID");

        Long totalPayments = paymentRepository.count();

        Double totalRevenue = paymentRepository.getTotalRevenue();

        if (totalRevenue == null) {
            totalRevenue = 0.0;
        }

        return new DashboardReportDTO(
                totalConsumers,
                totalMeterReadings,
                totalBills,
                paidBills,
                unpaidBills,
                totalPayments,
                totalRevenue
        );
    }
}