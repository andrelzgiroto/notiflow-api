package com.notiflow.api.task.service;

import com.notiflow.api.task.dto.CreateTaskRequest;
import com.notiflow.api.task.event.TaskEventMapper;
import com.notiflow.api.task.event.TaskEventType;
import com.notiflow.api.task.exception.InvalidTaskAssignmentException;
import com.notiflow.api.task.exception.TaskNotFoundException;
import com.notiflow.api.task.mapper.TaskMapper;
import com.notiflow.api.task.model.Task;
import com.notiflow.api.task.repository.TaskRepository;
import com.notiflow.api.user.model.User;
import com.notiflow.api.user.model.UserRole;
import com.notiflow.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class TaskService {

    private final ApplicationEventPublisher eventPublisher;

    private final UserService userService;

    private final TaskRepository taskRepository;

    @Transactional
    public Task create(CreateTaskRequest createTaskRequest) {
        User assignedBy = userService.findById(createTaskRequest.assignedById());

        if (assignedBy.getRole() != UserRole.MANAGER) {
            log.warn(
                    "Invalid task assignment: userId={} must have role MANAGER to assign tasks",
                    assignedBy.getId()
            );
            throw new InvalidTaskAssignmentException("assignedBy must be a user with role MANAGER");
        }

        User assignedTo = userService.findById(createTaskRequest.assignedToId());

        if (assignedTo.getRole() != UserRole.MEMBER) {
            log.warn(
                    "Invalid task assignment: userId={} must have role MEMBER to receive tasks",
                    assignedTo.getId()
            );
            throw new InvalidTaskAssignmentException("assignedTo must be a user with role MEMBER");
        }

        Task task = taskRepository.save(TaskMapper.toEntity(createTaskRequest, assignedBy, assignedTo));
        log.info("Task successfully created: taskId={}", task.getId());

        eventPublisher.publishEvent(TaskEventMapper.toEvent(task, TaskEventType.ASSIGNED));

        return task;
    }

    @Transactional
    public Task start(UUID id) {
        Task task = findById(id);
        task.start();

        Task updatedTask = taskRepository.save(task);
        log.info("Task started: taskId={}", updatedTask.getId());

        return updatedTask;
    }

    @Transactional
    public Task complete(UUID id) {
        Task task = findById(id);
        task.complete();

        Task updatedTask = taskRepository.save(task);
        log.info("Task completed: taskId={}", updatedTask.getId());

        eventPublisher.publishEvent(TaskEventMapper.toEvent(task, TaskEventType.COMPLETED));

        return updatedTask;
    }

    @Transactional(readOnly = true)
    public Page<Task> findAll(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Task findById(UUID id) {
        return taskRepository
                .findById(id)
                .orElseThrow(() -> {
                    log.warn("Task with this id does not exist: taskId={}", id);
                    return new TaskNotFoundException(id);
                });
    }
}
