package com.notiflow.api.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic taskEventsTopic() {
        return new NewTopic(
                "task-events",
                1,
                (short) 1
        );
    }
}
