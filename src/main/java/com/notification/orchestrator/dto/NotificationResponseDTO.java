package com.notification.orchestrator.dto;

import lombok.*;
import com.notification.orchestrator.entity.NotificationStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponseDTO {

    private Long id;
    private String channel;
    private String recipient;
    private String templateId;
    private NotificationStatus status;
    private int retryCount;
    private LocalDateTime createdAt;
}