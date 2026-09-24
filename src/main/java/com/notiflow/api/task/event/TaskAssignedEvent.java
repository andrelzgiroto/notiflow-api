package com.notiflow.api.task.event;

import java.time.Instant;
import java.util.UUID;

public record TaskAssignedEvent(
        UUID eventId,
        UUID taskId,
        String taskTitle,
        String taskDescription,
        UUID recipientId,
        String recipientEmail,
        String assignedByName,
        String assignedToName,
        Instant occurredAt
) {
}
