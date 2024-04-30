package com.ml.hotel_ml_auth_service.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicsConfiguration {

    @Bean
    public NewTopic jwtTopic(){
        return TopicBuilder.name("jwt_topic")
                .partitions(10)
                .replicas(2)
                .build();
    }

}
