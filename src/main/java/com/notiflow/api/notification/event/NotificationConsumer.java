package com.notiflow.api.notification.event;

import com.notiflow.api.notification.mapper.NotificationMapper;
import com.notiflow.api.notification.service.NotificationService;
import com.notiflow.api.task.event.TaskEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class NotificationConsumer {

    private final NotificationService notificationService;

    @KafkaListener(
            topics = "task-events",
            groupId = "notiflow-notification",
            containerFactory = "taskEventKafkaListenerContainerFactory"
    )
    private void consume(TaskEvent taskEvent) {
        notificationService.process(NotificationMapper.toRequest(taskEvent));
        log.info(
                "Task event consumed. eventId={}, type={}, taskId={}",
                taskEvent.eventId(),
                taskEvent.type(),
                taskEvent.taskId()
        );
    }
}
