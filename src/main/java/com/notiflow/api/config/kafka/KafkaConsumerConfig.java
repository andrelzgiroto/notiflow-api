package com.notiflow.api.config.kafka;

import com.notiflow.api.task.event.TaskEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.kafka.autoconfigure.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class KafkaConsumerConfig {

    private final KafkaProperties kafkaProperties;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TaskEvent> taskEventKafkaListenerContainerFactory(
            ConsumerFactory<String, TaskEvent> consumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, TaskEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);

        return factory;
    }
    @Bean
    public ConsumerFactory<String, TaskEvent> taskEventConsumerFactory() {

        Map<String, Object> properties = kafkaProperties.buildConsumerProperties();
        StringDeserializer stringDeserializer = new StringDeserializer();
        JacksonJsonDeserializer<TaskEvent> jsonDeserializer = new JacksonJsonDeserializer<>(TaskEvent.class);

        return new DefaultKafkaConsumerFactory<>(
                properties,
                stringDeserializer,
                jsonDeserializer
        );
    }
}