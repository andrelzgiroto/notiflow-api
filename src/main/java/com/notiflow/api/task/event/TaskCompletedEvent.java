package com.notiflow.api.task.event;

import java.time.Instant;
import java.util.UUID;

public record TaskCompletedEvent(
        UUID eventId,
        UUID taskId,
        String taskTitle,
        String taskDescription,
        Instant taskFinishedAt,
        UUID recipientId,
        String recipientEmail,
        String assignedByName,
        String assignedToName,
        Instant occurredAt
) {
}
