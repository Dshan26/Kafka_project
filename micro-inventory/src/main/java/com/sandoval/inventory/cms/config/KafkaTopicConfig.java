package com.sandoval.inventory.cms.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

  @Value("${spring.kafka.topic.inventory}")
  private String inventoryTopic;

  @Bean
  NewTopic inventoryTopic() {
    return TopicBuilder.name(inventoryTopic)
            .build();
  }
}