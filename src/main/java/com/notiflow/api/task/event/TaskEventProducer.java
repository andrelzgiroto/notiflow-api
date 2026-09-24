package com.notiflow.api.task.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class TaskEventProducer {

    private final KafkaTemplate<String, TaskEvent> kafkaTemplate;

    public void publish(TaskEvent taskEvent) {
        kafkaTemplate.send(
                "task-events",
                taskEvent.taskId().toString(),
                taskEvent
        );

        log.info(
                "Task event publication requested. eventId={}, type={}, taskId={}",
                taskEvent.eventId(),
                taskEvent.type(),
                taskEvent.taskId()
        );
    }
}
