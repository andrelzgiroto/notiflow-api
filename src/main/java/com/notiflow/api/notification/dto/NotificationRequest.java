package com.notiflow.api.notification.dto;

import com.notiflow.api.notification.model.NotificationType;

import java.time.Instant;
import java.util.UUID;

public record NotificationRequest(
        UUID eventId,
        UUID taskId,
        String taskTitle,
        String taskDescription,
        Instant taskFinishedAt,
        UUID recipientId,
        String recipientEmail,
        String assignedByName,
        String assignedToName,
        NotificationType notificationType
) {
}
