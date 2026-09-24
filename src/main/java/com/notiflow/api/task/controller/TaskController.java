package com.notiflow.api.task.controller;

import com.notiflow.api.task.dto.CreateTaskRequest;
import com.notiflow.api.task.dto.TaskResponse;
import com.notiflow.api.task.mapper.TaskMapper;
import com.notiflow.api.task.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/tasks")
@RestController
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponse> create(@RequestBody @Valid CreateTaskRequest createTaskRequest) {
        TaskResponse response = TaskMapper.toResponse(taskService.create(createTaskRequest));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<TaskResponse> start(@PathVariable UUID id) {
        TaskResponse response = TaskMapper.toResponse(taskService.start(id));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<TaskResponse> complete(@PathVariable UUID id) {
        TaskResponse response = TaskMapper.toResponse(taskService.complete(id));

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<TaskResponse>> findAll(Pageable pageable) {
        Page<TaskResponse> responses = taskService
                .findAll(pageable)
                .map(TaskMapper::toResponse);

        return ResponseEntity.ok(responses);
    }
}
