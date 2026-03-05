package com.notification.orchestrator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.notification.orchestrator.exception.SystemOverloadedException;
import com.notification.orchestrator.dto.NotificationRequestDTO;
import com.notification.orchestrator.dto.NotificationResponseDTO;
import com.notification.orchestrator.entity.Notification;
import com.notification.orchestrator.entity.NotificationStatus;
import com.notification.orchestrator.repository.NotificationRepository;
import com.notification.orchestrator.queue.NotificationPublisher;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.QueueInformation;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;
    private final NotificationProcessor processor;
    private final NotificationPublisher publisher;
    private final AmqpAdmin amqpAdmin;

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

        QueueInformation queueInfo = amqpAdmin.getQueueInfo("notification.queue");
        if (queueInfo != null && queueInfo.getMessageCount() > 10) {
        throw new SystemOverloadedException("System overloaded. Please retry later.");
        }

        publisher.publish(saved.getId());

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

    @Override
    public NotificationResponseDTO getNotificationById(Long id) {
        Notification notification = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        return NotificationResponseDTO.builder()
                .id(notification.getId())
                .channel(notification.getChannel())
                .recipient(notification.getRecipient())
                .templateId(notification.getTemplateId())
                .status(notification.getStatus())
                .retryCount(notification.getRetryCount())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}