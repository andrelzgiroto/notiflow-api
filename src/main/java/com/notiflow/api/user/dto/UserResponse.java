package com.notiflow.api.user.dto;

import com.notiflow.api.user.model.UserRole;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String name,
        String email,
        UserRole role
) {
}
