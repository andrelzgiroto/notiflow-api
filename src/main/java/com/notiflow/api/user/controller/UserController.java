package com.notiflow.api.user.controller;

import com.notiflow.api.user.dto.CreateUserRequest;
import com.notiflow.api.user.dto.UserResponse;
import com.notiflow.api.user.mapper.UserMapper;
import com.notiflow.api.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody @Valid CreateUserRequest createUserRequest) {
        UserResponse response = UserMapper.toResponse(userService.create(createUserRequest));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<UserResponse>> findAll(Pageable pageable) {
        Page<UserResponse> responses = userService
                .findAll(pageable)
                .map(UserMapper::toResponse);

        return ResponseEntity.ok(responses);
    }
}
