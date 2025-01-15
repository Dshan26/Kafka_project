package com.sandoval.inventory.cms.kafka;

import com.sandoval.inventory.cms.service.InventoryService;
import com.sandoval.model.cms.model.Order;
import com.sandoval.model.cms.model.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class InventoryConsumer {

  private static final Logger LOGGER = LoggerFactory.getLogger(InventoryConsumer.class);

  private final InventoryService inventoryService;

  public InventoryConsumer(InventoryService inventoryService) {
    this.inventoryService = inventoryService;
  }

  /**
   * Consume eventos del tópico de órdenes y los procesa.
   *
   * @param orderEvent Evento de orden recibido.
   */
  @KafkaListener(topics = "${spring.kafka.topic.order}", groupId = "${spring.kafka.consumer.group-id}")
  public void consumeOrderEvent(OrderEvent orderEvent) {
    LOGGER.info(String.format("Orden received -> %s", orderEvent.toString()));
    try {
      inventoryService.processOrder(orderEvent);
    } catch (Exception e) {
      LOGGER.error("Error processing order: {}", e.getMessage());
    }
  }
}