package com.sandoval.notification.cms.kafka;

import com.sandoval.model.cms.model.InventoryEvent;
import com.sandoval.notification.cms.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

  private static final Logger LOGGER = LoggerFactory.getLogger(NotificationConsumer.class);
  private final NotificationService notificationService;

  public NotificationConsumer(NotificationService notificationService) {
    this.notificationService = notificationService;
  }

  @KafkaListener(topics = "${spring.kafka.topic.inventory}", groupId = "${spring.kafka.consumer.group-id}")
  public void consumeInventoryEvent(InventoryEvent inventoryEvent) {
    LOGGER.info("Received Inventory Event: {}", inventoryEvent);
    notificationService.sendNotification(inventoryEvent);
  }
}