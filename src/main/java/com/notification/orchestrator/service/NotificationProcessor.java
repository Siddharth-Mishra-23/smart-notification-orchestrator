package com.notification.orchestrator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.notification.orchestrator.entity.Notification;
import com.notification.orchestrator.entity.NotificationStatus;
import com.notification.orchestrator.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class NotificationProcessor {

    private static final Logger log = LoggerFactory.getLogger(NotificationProcessor.class);
    private static final int MAX_RETRY_ATTEMPTS = 3;

    private final NotificationRepository repository;

    public void processNotification(Notification notification) {

        try {
            Thread.sleep(3000); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        notification.setStatus(NotificationStatus.PROCESSING);
        notification.setUpdatedAt(LocalDateTime.now());
        repository.save(notification);

        log.info("Notification {} moved to PROCESSING", notification.getId());

        boolean success = new Random().nextBoolean();

        if (success) {
            notification.setStatus(NotificationStatus.SENT);
            notification.setUpdatedAt(LocalDateTime.now());
            repository.save(notification);

            log.info("Notification {} SENT successfully", notification.getId());
            return;
        }

        int retries = notification.getRetryCount() + 1;
        notification.setRetryCount(retries);

        if (retries < MAX_RETRY_ATTEMPTS) {

            notification.setStatus(NotificationStatus.RETRYING);
            notification.setUpdatedAt(LocalDateTime.now());
            repository.save(notification);

            log.info("Notification {} FAILED. Retrying attempt {}/{}",
                    notification.getId(), retries, MAX_RETRY_ATTEMPTS);

            processNotification(notification);

        } 
        else {

            notification.setStatus(NotificationStatus.FAILED);
            notification.setUpdatedAt(LocalDateTime.now());
            repository.save(notification);

            log.info("Notification {} permanently FAILED after {} attempts",
                    notification.getId(), MAX_RETRY_ATTEMPTS);
        }
    }
}