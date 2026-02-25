package com.notification.orchestrator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.notification.orchestrator.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}