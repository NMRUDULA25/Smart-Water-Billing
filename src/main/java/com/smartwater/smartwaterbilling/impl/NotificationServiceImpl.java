package com.smartwater.smartwaterbilling.impl;

import com.smartwater.smartwaterbilling.entity.Notification;
import com.smartwater.smartwaterbilling.repository.NotificationRepository;
import com.smartwater.smartwaterbilling.service.NotificationService;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl
        implements NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(
            NotificationRepository notificationRepository) {

        this.notificationRepository =
                notificationRepository;
    }

    @Override
    public Notification createNotification(
            String consumerNumber,
            String type,
            String message) {

        Notification notification =
                new Notification();

        notification.setConsumerNumber(
                consumerNumber);

        notification.setType(type);

        notification.setMessage(message);

        notification.setRead(false);

        notification.setCreatedAt(
                LocalDateTime.now());

        return notificationRepository.save(
                notification);
    }

    @Override
    public List<Notification> getAllNotifications() {

        return notificationRepository
                .findByOrderByCreatedAtDesc();
    }

    @Override
    public List<Notification> getUnreadNotifications() {

        return notificationRepository
                .findByReadFalse();
    }

    @Override
    public void markAsRead(Long id) {

        Notification notification =
                notificationRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Notification not found"));

        notification.setRead(true);

        notificationRepository.save(notification);
    }

    @Override
    public void deleteNotification(Long id) {

        if (!notificationRepository.existsById(id)) {

            throw new RuntimeException(
                    "Notification not found");
        }

        notificationRepository.deleteById(id);
    }
}