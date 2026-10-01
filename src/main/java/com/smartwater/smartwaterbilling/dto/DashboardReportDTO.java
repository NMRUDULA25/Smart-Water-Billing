package com.smartwater.smartwaterbilling.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardReportDTO {

    private Long totalConsumers;

    private Long totalMeterReadings;

    private Long totalBills;

    private Long paidBills;

    private Long unpaidBills;

    private Long totalPayments;

    private Double totalRevenue;

}