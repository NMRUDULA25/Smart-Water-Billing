package com.smartwater.smartwaterbilling.controller;

import com.smartwater.smartwaterbilling.entity.Notification;
import com.smartwater.smartwaterbilling.service.NotificationService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "http://localhost:5173"
})
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(
            NotificationService notificationService) {

        this.notificationService =
                notificationService;
    }

    @GetMapping
    public List<Notification> getAllNotifications() {

        return notificationService
                .getAllNotifications();
    }

    @GetMapping("/unread")
    public List<Notification> getUnreadNotifications() {

        return notificationService
                .getUnreadNotifications();
    }

    @PutMapping("/{id}/read")
    public void markAsRead(
            @PathVariable Long id) {

        notificationService.markAsRead(id);
    }

    @DeleteMapping("/{id}")
    public void deleteNotification(
            @PathVariable Long id) {

        notificationService.deleteNotification(id);
    }
}