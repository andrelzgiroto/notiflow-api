package com.notiflow.api.notification.mapper;

import com.notiflow.api.notification.dto.NotificationRequest;
import com.notiflow.api.notification.model.Notification;
import com.notiflow.api.notification.model.NotificationType;
import com.notiflow.api.task.event.TaskAssignedEvent;
import com.notiflow.api.task.event.TaskCompletedEvent;

public class NotificationMapper {

    public static NotificationRequest toRequest(TaskAssignedEvent taskAssignedEvent) {
        return new NotificationRequest(
                taskAssignedEvent.eventId(),
                taskAssignedEvent.taskId(),
                taskAssignedEvent.taskTitle(),
                taskAssignedEvent.taskDescription(),
                null,
                taskAssignedEvent.recipientId(),
                taskAssignedEvent.recipientEmail(),
                taskAssignedEvent.assignedByName(),
                taskAssignedEvent.assignedToName(),
                NotificationType.TASK_ASSIGNED
        );
    }

    public static NotificationRequest toRequest(TaskCompletedEvent taskCompletedEvent) {
        return new NotificationRequest(
                taskCompletedEvent.eventId(),
                taskCompletedEvent.taskId(),
                taskCompletedEvent.taskTitle(),
                taskCompletedEvent.taskDescription(),
                taskCompletedEvent.taskFinishedAt(),
                taskCompletedEvent.recipientId(),
                taskCompletedEvent.recipientEmail(),
                taskCompletedEvent.assignedByName(),
                taskCompletedEvent.assignedToName(),
                NotificationType.TASK_COMPLETED
        );
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

