package com.notification.orchestrator.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String channel;

    private String recipient;

    private String templateId;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    private int retryCount;

    private String providerUsed;

    private String errorMessage;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}