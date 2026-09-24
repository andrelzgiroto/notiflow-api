package com.notiflow.api.notification.model;

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
@Table(name = "notifications")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "event_id", nullable = false, unique = true)
    private UUID eventId;

    @Column(name = "task_id", nullable = false)
    private UUID taskId;

    @Column(name = "task_title", nullable = false)
    private String taskTitle;

    @Column(name = "task_description", columnDefinition = "TEXT")
    private String taskDescription;

    @Column(name = "task_finished_at")
    private Instant taskFinishedAt;

    @Column(name = "recipient_id", nullable = false)
    private UUID recipientId;

    @Column(name = "recipient_email", nullable = false)
    private String recipientEmail;

    @Column(name = "assigned_by_name", nullable = false)
    private String assignedByName;

    @Column(name = "assigned_to_name", nullable = false)
    private String assignedToName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationStatus status;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "sent_at")
    private Instant sentAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "failure_reason")
    private NotificationFailureReason failureReason;

    public Notification(
            UUID eventId,
            UUID taskId,
            String taskTitle,
            String taskDescription,
            Instant taskFinishedAt,
            UUID recipientId,
            String recipientEmail,
            String assignedByName,
            String assignedToName,
            NotificationType type
    ) {
        this.eventId = eventId;
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskFinishedAt = taskFinishedAt;
        this.recipientId = recipientId;
        this.recipientEmail = recipientEmail;
        this.assignedByName = assignedByName;
        this.assignedToName = assignedToName;
        this.type = type;
        this.status = NotificationStatus.PENDING;
    }

    public void markAsSent() {
        this.status = NotificationStatus.SENT;
        this.sentAt = Instant.now();
        this.failureReason = null;
    }

    public void markAsFailed(NotificationFailureReason failureReason) {
        this.status = NotificationStatus.FAILED;
        this.failureReason = failureReason;
    }
}