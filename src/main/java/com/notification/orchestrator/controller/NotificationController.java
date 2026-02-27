package com.notification.orchestrator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.notification.orchestrator.dto.NotificationRequestDTO;
import com.notification.orchestrator.dto.NotificationResponseDTO;
import com.notification.orchestrator.service.NotificationService;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public NotificationResponseDTO createNotification(
            @Valid @RequestBody NotificationRequestDTO request) {

        return notificationService.createNotification(request);
    }

    @GetMapping("/{id}")
    public NotificationResponseDTO getNotification(@PathVariable Long id) {
    return notificationService.getNotificationById(id);
    }
}