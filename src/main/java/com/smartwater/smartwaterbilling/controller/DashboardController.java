package com.smartwater.smartwaterbilling.controller;

import com.smartwater.smartwaterbilling.dto.DashboardReportDTO;
import com.smartwater.smartwaterbilling.service.ReportService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:3000")
public class DashboardController {

    private final ReportService reportService;

    public DashboardController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public DashboardReportDTO getDashboard() {
        return reportService.getDashboardReport();
    }
}