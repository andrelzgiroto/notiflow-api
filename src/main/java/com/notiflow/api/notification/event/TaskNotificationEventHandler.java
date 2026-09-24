package com.notiflow.api.notification.event;

import com.notiflow.api.notification.mapper.NotificationMapper;
import com.notiflow.api.notification.service.NotificationService;
import com.notiflow.api.task.event.TaskAssignedEvent;
import com.notiflow.api.task.event.TaskCompletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TaskNotificationEventHandler {

    private final NotificationService notificationService;

    @EventListener
    public void handle(TaskAssignedEvent taskAssignedEvent) {
        notificationService.process(NotificationMapper.toRequest(taskAssignedEvent));
    }

    @EventListener
    public void handle(TaskCompletedEvent taskCompletedEvent) {
        notificationService.process(NotificationMapper.toRequest(taskCompletedEvent));
    }
}
