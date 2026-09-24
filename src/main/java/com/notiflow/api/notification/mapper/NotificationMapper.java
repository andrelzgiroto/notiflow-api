package com.notiflow.api.notification.mapper;

import com.notiflow.api.notification.dto.NotificationRequest;
import com.notiflow.api.notification.model.Notification;
import com.notiflow.api.notification.model.NotificationType;
import com.notiflow.api.task.event.TaskEvent;

public class NotificationMapper {

    public static NotificationRequest toRequest(TaskEvent taskEvent) {
        return switch (taskEvent.type()) {
            case ASSIGNED -> new NotificationRequest(
                    taskEvent.eventId(),
                    taskEvent.taskId(),
                    taskEvent.taskTitle(),
                    taskEvent.taskDescription(),
                    taskEvent.taskFinishedAt(),
                    taskEvent.assignedToId(),
                    taskEvent.assignedToEmail(),
                    taskEvent.assignedByName(),
                    taskEvent.assignedToName(),
                    NotificationType.TASK_ASSIGNED
            );

            case COMPLETED -> new NotificationRequest(
                    taskEvent.eventId(),
                    taskEvent.taskId(),
                    taskEvent.taskTitle(),
                    taskEvent.taskDescription(),
                    taskEvent.taskFinishedAt(),
                    taskEvent.assignedById(),
                    taskEvent.assignedByEmail(),
                    taskEvent.assignedByName(),
                    taskEvent.assignedToName(),
                    NotificationType.TASK_COMPLETED
            );
        };
    }

    public static Notification toEntity(NotificationRequest notificationRequest) {
        return new Notification(
                notificationRequest.eventId(),
                notificationRequest.taskId(),
                notificationRequest.taskTitle(),
                notificationRequest.taskDescription(),
                notificationRequest.taskFinishedAt(),
                notificationRequest.recipientId(),
                notificationRequest.recipientEmail(),
                notificationRequest.assignedByName(),
                notificationRequest.assignedToName(),
                notificationRequest.notificationType()
        );
    }
}

