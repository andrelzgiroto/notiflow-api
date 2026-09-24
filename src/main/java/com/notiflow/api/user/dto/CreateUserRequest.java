package com.notiflow.api.user.dto;

import com.notiflow.api.user.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(

        @NotBlank(message = "Name is required.")
        @Size(max = 100, message = "Name must not exceed 100 characters.")
        String name,

        @NotBlank(message = "Email is required.")
        @Email(message = "Email must be valid.")
        @Size(max = 150, message = "Email must not exceed 150 characters.")
        String email,

        @NotNull(message = "Role is required.")
        UserRole role

) {
}