package com.notiflow.api.task.mapper;

import com.notiflow.api.task.dto.CreateTaskRequest;
import com.notiflow.api.task.dto.TaskResponse;
import com.notiflow.api.task.model.Task;
import com.notiflow.api.user.model.User;

public class TaskMapper {

    public static Task toEntity(
            CreateTaskRequest createTaskRequest,
            User assignedBy,
            User assignedTo
    ) {
        return new Task(
                createTaskRequest.title(),
                createTaskRequest.description(),
                assignedBy,
                assignedTo
        );
    }

    public static TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getAssignedBy().getId(),
                task.getAssignedTo().getId(),
                task.getCreatedAt(),
                task.getFinishedAt()
        );
    }
}
