package com.smartwater.smartwaterbilling.controller;

import com.smartwater.smartwaterbilling.dto.DashboardReportDTO;
import com.smartwater.smartwaterbilling.service.ReportService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "http://localhost:5173")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/dashboard")
    public DashboardReportDTO getDashboardReport() {
        return reportService.getDashboardReport();
    }
}