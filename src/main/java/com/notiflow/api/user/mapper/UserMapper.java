package com.notiflow.api.user.mapper;

import com.notiflow.api.user.dto.CreateUserRequest;
import com.notiflow.api.user.dto.UserResponse;
import com.notiflow.api.user.model.User;

public class UserMapper {

    public static User toEntity(CreateUserRequest createUserRequest) {
        return new User(
                createUserRequest.name(),
                createUserRequest.email(),
                createUserRequest.role()
        );
    }

    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
