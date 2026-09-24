package com.notiflow.api.task.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateTaskRequest(

        @NotBlank(message = "Title is required.")
        @Size(max = 150, message = "Title must not exceed 150 characters.")
        String title,

        String description,

        @NotNull(message = "assignedById is required.")
        UUID assignedById,

        @NotNull(message = "assignedToId is required.")
        UUID assignedToId
) {
}