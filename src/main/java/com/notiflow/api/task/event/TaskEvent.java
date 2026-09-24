package com.notiflow.api.task.event;

import java.time.Instant;
import java.util.UUID;

public record TaskEvent(
        UUID eventId,

        TaskEventType type,

        UUID taskId,
        String taskTitle,
        String taskDescription,
        Instant taskFinishedAt,

        UUID assignedById,
        String assignedByEmail,
        String assignedByName,

        UUID assignedToId,
        String assignedToEmail,
        String assignedToName,

        Instant occurredAt
){
}
