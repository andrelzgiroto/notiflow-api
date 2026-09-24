package com.notiflow.api.task.event.mapper;

import com.notiflow.api.task.event.TaskAssignedEvent;
import com.notiflow.api.task.event.TaskCompletedEvent;
import com.notiflow.api.task.model.Task;

import java.time.Instant;
import java.util.UUID;

public class TaskEventMapper {

    public static TaskAssignedEvent toTaskAssignedEvent(Task task) {
        return new TaskAssignedEvent(
                UUID.randomUUID(),
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getAssignedTo().getId(),
                task.getAssignedTo().getEmail(),
                task.getAssignedBy().getName(),
                task.getAssignedTo().getName(),
                Instant.now()
        );
    }

    public static TaskCompletedEvent toTaskCompletedEvent(Task task) {
        return new TaskCompletedEvent(
                UUID.randomUUID(),
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getFinishedAt(),
                task.getAssignedBy().getId(),
                task.getAssignedBy().getEmail(),
                task.getAssignedBy().getName(),
                task.getAssignedTo().getName(),
                Instant.now()
        );
    }

}
