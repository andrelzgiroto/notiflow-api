package com.notiflow.api.task.event;

import com.notiflow.api.task.model.Task;

import java.time.Instant;
import java.util.UUID;

public class TaskEventMapper {

    public static TaskEvent toEvent(Task task, TaskEventType type) {
        return new TaskEvent(
                UUID.randomUUID(),
                type,
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getFinishedAt(),
                task.getAssignedBy().getId(),
                task.getAssignedBy().getEmail(),
                task.getAssignedBy().getName(),
                task.getAssignedTo().getId(),
                task.getAssignedTo().getEmail(),
                task.getAssignedTo().getName(),
                Instant.now()
        );
    }
}
