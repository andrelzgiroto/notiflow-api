package com.notiflow.api.task.model;

import com.notiflow.api.task.exception.InvalidTaskStatusTransitionException;
import com.notiflow.api.user.model.User;
import com.notiflow.api.user.model.UserRole;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void shouldCreateTaskAsPending() {
        Task task = createTask();

        assertAll(
                () -> assertEquals(TaskStatus.PENDING, task.getStatus()),
                () -> assertNull(task.getFinishedAt())
        );
    }

    @Test
    void shouldStartPendingTask() {
        Task task = createTask();

        task.start();

        assertEquals(TaskStatus.IN_PROGRESS, task.getStatus());
    }

    @Test
    void shouldCompleteInProgressTask() {
        Task task = createTask();
        task.start();

        task.complete();

        assertAll(
                () -> assertEquals(TaskStatus.COMPLETED, task.getStatus()),
                () -> assertNotNull(task.getFinishedAt())
        );
    }

    @Test
    void shouldRejectStartWhenTaskIsNotPending() {
        Task task = createTask();
        task.start();

        assertThrows(
                InvalidTaskStatusTransitionException.class,
                task::start
        );
    }

    @Test
    void shouldRejectCompletionWhenTaskIsNotInProgress() {
        Task task = createTask();

        assertThrows(
                InvalidTaskStatusTransitionException.class,
                task::complete
        );
    }

    private Task createTask() {
        User manager = new User(
                "Manager",
                "manager@notiflow.com",
                UserRole.MANAGER
        );

        User member = new User(
                "Member",
                "member@notiflow.com",
                UserRole.MEMBER
        );

        return new Task(
                "Implement notification flow",
                "Task description",
                manager,
                member
        );
    }
}