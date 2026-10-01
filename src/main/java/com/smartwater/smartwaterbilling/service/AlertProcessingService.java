package com.smartwater.smartwaterbilling.service;

import com.smartwater.smartwaterbilling.dto.AnomalyDTO;
import com.smartwater.smartwaterbilling.entity.User;
import com.smartwater.smartwaterbilling.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertProcessingService {

    private final AnomalyDetectionService anomalyDetectionService;
    private final NotificationService notificationService;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public AlertProcessingService(
            AnomalyDetectionService anomalyDetectionService,
            NotificationService notificationService,
            UserRepository userRepository,
            EmailService emailService) {

        this.anomalyDetectionService = anomalyDetectionService;
        this.notificationService = notificationService;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @Scheduled(fixedRate = 60000)
    public void processAlerts() {

        try {

            List<AnomalyDTO> anomalies =
                    anomalyDetectionService.detectAnomalies();

            if (anomalies.isEmpty()) {

                System.out.println(
                        "No anomalies detected."
                );

            } else {

                System.out.println(
                        "Anomalies detected: "
                                + anomalies.size()
                );

                for (AnomalyDTO anomaly : anomalies) {

                    String consumerNumber =
                            anomaly.getConsumerNumber();

                    System.out.println(
                            "Consumer: "
                                    + consumerNumber
                    );

                    System.out.println(
                            "Previous Consumption: "
                                    + anomaly.getPreviousConsumption()
                    );

                    System.out.println(
                            "Current Consumption: "
                                    + anomaly.getCurrentConsumption()
                    );

                    System.out.println(
                            "Increase: "
                                    + anomaly.getIncreasePercentage()
                                    + "%"
                    );

                    // -----------------------------
                    // 1. CREATE IN-APP NOTIFICATION
                    // -----------------------------

                    notificationService.createNotification(
                            consumerNumber,
                            anomaly.getStatus(),
                            anomaly.getMessage()
                    );

                    // -----------------------------
                    // 2. FIND USER BY CONSUMER NUMBER
                    // -----------------------------

                    User user =
                            userRepository
                                    .findByConsumerNumber(
                                            consumerNumber
                                    )
                                    .orElse(null);

                    if (user == null) {

                        System.out.println(
                                "No user found for consumer: "
                                        + consumerNumber
                        );

                        continue;
                    }

                    // -----------------------------
                    // 3. GET USER EMAIL
                    // -----------------------------

                    String email = user.getEmail();

                    if (email == null || email.isBlank()) {

                        System.out.println(
                                "No email address found for consumer: "
                                        + consumerNumber
                        );

                        continue;
                    }

                    System.out.println(
                            "Sending alert email to: "
                                    + email
                    );

                    // -----------------------------
                    // 4. SEND EMAIL
                    // -----------------------------

                    emailService.sendAlertEmail(
                            email,
                            consumerNumber,
                            "Smart Water Billing - Water Consumption Alert",
                            anomaly.getMessage()
                    );

                    System.out.println(
                            "Alert email sent successfully to: "
                                    + email
                    );
                }
            }

        } catch (Exception e) {

            System.err.println(
                    "Error while processing alerts:"
            );

            e.printStackTrace();
        }

        System.out.println(
                "Scheduled alert processing completed."
        );
    }
}