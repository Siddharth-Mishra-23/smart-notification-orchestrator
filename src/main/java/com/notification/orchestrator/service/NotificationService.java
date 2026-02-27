package com.notification.orchestrator.service;

import com.notification.orchestrator.dto.NotificationRequestDTO;
import com.notification.orchestrator.dto.NotificationResponseDTO;

public interface NotificationService {

    NotificationResponseDTO createNotification(NotificationRequestDTO request);
    NotificationResponseDTO getNotificationById(Long id);
}