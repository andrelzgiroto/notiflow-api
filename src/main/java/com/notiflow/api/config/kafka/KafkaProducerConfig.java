package com.notiflow.api.config.kafka;

import com.notiflow.api.task.event.TaskEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.kafka.autoconfigure.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;

import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class KafkaProducerConfig {

    private final KafkaProperties kafkaProperties;

    @Bean
    public KafkaTemplate<String, TaskEvent> taskEventKafkaTemplate(
            ProducerFactory<String, TaskEvent> producerFactory
    ) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public ProducerFactory<String, TaskEvent> taskEventProducerFactory() {

        Map<String, Object> properties = kafkaProperties.buildProducerProperties();
        StringSerializer stringSerializer = new StringSerializer();
        JacksonJsonSerializer<TaskEvent> jsonSerializer = new JacksonJsonSerializer<>();

        return new DefaultKafkaProducerFactory<>(
                properties,
                stringSerializer,
                jsonSerializer
        );
    }
}
