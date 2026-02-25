package com.notification.orchestrator.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequestDTO {

    @NotBlank
    private String channel;

    @NotBlank
    private String recipient;

    @NotBlank
    private String templateId;
}