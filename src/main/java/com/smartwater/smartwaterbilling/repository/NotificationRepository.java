package com.smartwater.smartwaterbilling.repository;

import com.smartwater.smartwaterbilling.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository
        extends JpaRepository<Notification, Long> {

    List<Notification> findByConsumerNumber(
            String consumerNumber);

    List<Notification> findByReadFalse();

    List<Notification> findByOrderByCreatedAtDesc();
}