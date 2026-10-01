package com.smartwater.smartwaterbilling.impl;

import com.smartwater.smartwaterbilling.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendAlertEmail(
            String to,
            String consumerNumber,
            String subject,
            String message) {

        SimpleMailMessage mail =
                new SimpleMailMessage();

        mail.setTo(to);
        mail.setSubject(subject);

        mail.setText(
                "Smart Water Billing Alert\n\n" +
                        "Consumer Number: " +
                        consumerNumber +
                        "\n\n" +
                        message +
                        "\n\n" +
                        "Please check your water consumption."
        );

        mailSender.send(mail);
    }
}