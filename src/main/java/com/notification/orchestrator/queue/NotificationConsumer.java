package com.notification.orchestrator.queue;

import com.notification.orchestrator.config.RabbitMQConfig;
import com.notification.orchestrator.entity.Notification;
import com.notification.orchestrator.repository.NotificationRepository;
import com.notification.orchestrator.service.NotificationProcessor;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationRepository repository;
    private final NotificationProcessor processor;

    @RabbitListener(
            queues = RabbitMQConfig.NOTIFICATION_QUEUE,
            containerFactory = "rabbitListenerContainerFactory"
    )
    public void consume(Long notificationId,
                        Channel channel,
                        @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {

        try {

            try {
                Thread.sleep(30000); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread interrupted", e);
            }

            Notification notification = repository.findById(notificationId)
                    .orElseThrow(() -> new RuntimeException("Notification not found"));

            processor.processNotification(notification);

            channel.basicAck(tag, false);

        } catch (Exception e) {

            channel.basicNack(tag, false, false);
            throw e;
        }
    }
}