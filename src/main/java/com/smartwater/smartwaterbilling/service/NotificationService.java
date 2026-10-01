package com.smartwater.smartwaterbilling.service;

import com.smartwater.smartwaterbilling.entity.Notification;

import java.util.List;

public interface NotificationService {

    Notification createNotification(
            String consumerNumber,
            String type,
            String message);

    List<Notification> getAllNotifications();

    List<Notification> getUnreadNotifications();

    void markAsRead(Long id);

    void deleteNotification(Long id);
}