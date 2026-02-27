package com.notification.orchestrator.queue;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import com.notification.orchestrator.config.RabbitMQConfig;
import com.notification.orchestrator.repository.NotificationRepository;
import com.notification.orchestrator.service.NotificationProcessor;
import com.notification.orchestrator.entity.Notification;

@Component
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationRepository repository;
    private final NotificationProcessor processor;

    @RabbitListener(queues = RabbitMQConfig.NOTIFICATION_QUEUE)
    public void consume(Long notificationId) {

        Notification notification = repository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        processor.processNotification(notification);
    }
}