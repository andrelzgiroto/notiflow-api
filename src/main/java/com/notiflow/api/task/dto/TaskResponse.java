package com.notiflow.api.task.dto;

import com.notiflow.api.task.model.TaskStatus;

import java.time.Instant;
import java.util.UUID;

public record TaskResponse(
        UUID id,
        String title,
        String description,
        TaskStatus status,
        UUID assignedBy,
        UUID assignedTo,
        Instant createdAt,
        Instant finishedAt
) {
}
