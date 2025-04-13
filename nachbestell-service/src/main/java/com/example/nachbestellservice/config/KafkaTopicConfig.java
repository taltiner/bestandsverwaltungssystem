package com.example.nachbestellservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

public class KafkaTopicConfig {

    @Bean
    public NewTopic bestellStatusTopic() {
        return TopicBuilder.name("nachbestellung").build();
    }
}
