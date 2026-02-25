package com.notification.orchestrator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.notification.orchestrator.dto.NotificationRequestDTO;
import com.notification.orchestrator.dto.NotificationResponseDTO;
import com.notification.orchestrator.entity.Notification;
import com.notification.orchestrator.entity.NotificationStatus;
import com.notification.orchestrator.repository.NotificationRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;

    @Override
    public NotificationResponseDTO createNotification(NotificationRequestDTO request) {

        Notification notification = Notification.builder()
                .channel(request.getChannel())
                .recipient(request.getRecipient())
                .templateId(request.getTemplateId())
                .status(NotificationStatus.PENDING)
                .retryCount(0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Notification saved = repository.save(notification);

        return NotificationResponseDTO.builder()
                .id(saved.getId())
                .channel(saved.getChannel())
                .recipient(saved.getRecipient())
                .templateId(saved.getTemplateId())
                .status(saved.getStatus())
                .retryCount(saved.getRetryCount())
                .createdAt(saved.getCreatedAt())
                .build();
    }
}