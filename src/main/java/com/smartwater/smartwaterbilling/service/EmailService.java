package com.smartwater.smartwaterbilling.service;

public interface EmailService {

    void sendAlertEmail(
            String to,
            String consumerNumber,
            String subject,
            String message
    );
}