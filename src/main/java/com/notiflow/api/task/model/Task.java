package com.notiflow.api.task.model;

import com.notiflow.api.task.exception.InvalidTaskStatusTransitionException;
import com.notiflow.api.user.model.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tasks")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "assigned_by_id", nullable = false)
    private User assignedBy;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "assigned_to_id", nullable = false)
    private User assignedTo;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "finished_at")
    private Instant finishedAt;

    public Task(
            String title,
            String description,
            User assignedBy,
            User assignedTo
    ) {
        this.title = title;
        this.description = description;
        this.status = TaskStatus.PENDING;
        this.assignedBy = assignedBy;
        this.assignedTo = assignedTo;
    }

    public void start() {
        if (status != TaskStatus.PENDING) {
            throw new InvalidTaskStatusTransitionException("Task must be PENDING in order to be started.");
        }
        this.status = TaskStatus.IN_PROGRESS;
    }

    public void complete() {
        if (status != TaskStatus.IN_PROGRESS) {
            throw new InvalidTaskStatusTransitionException("Task must be IN PROGRESS in order to be completed.");
        }
        this.status = TaskStatus.COMPLETED;
        this.finishedAt = Instant.now();
    }
}
