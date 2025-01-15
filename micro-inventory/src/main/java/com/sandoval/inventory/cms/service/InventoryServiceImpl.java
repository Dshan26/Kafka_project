package com.sandoval.inventory.cms.service;

import com.sandoval.model.cms.model.InventoryEvent;
import com.sandoval.model.cms.model.OrderEvent;
import com.sandoval.model.cms.model.entities.Inventory;
import com.sandoval.model.cms.model.repository.InventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {

  private static final Logger LOGGER = LoggerFactory.getLogger(InventoryServiceImpl.class);

  private final InventoryRepository inventoryRepository;
  private final KafkaTemplate<String, InventoryEvent> kafkaTemplate;

  @Value("${spring.kafka.topic.inventory}")
  private String inventoryTopic;

  public InventoryServiceImpl(InventoryRepository inventoryRepository, KafkaTemplate<String, InventoryEvent> kafkaTemplate) {
    this.inventoryRepository = inventoryRepository;
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public void processOrder(OrderEvent orderEvent) {
    InventoryEvent inventoryEvent = new InventoryEvent();

    try {
      Optional<Inventory> inventoryOpt = inventoryRepository.findByProductName(orderEvent.getOrder().getProductName());

      if (inventoryOpt.isPresent() && inventoryOpt.get().getQuantity() >= orderEvent.getOrder().getQuantity()) {
        // Reduce el inventario y guarda los cambios
        Inventory inventory = inventoryOpt.get();
        inventory.setQuantity(inventory.getQuantity() - orderEvent.getOrder().getQuantity());
        inventoryRepository.save(inventory);

        // Configura el evento como reservado
        inventoryEvent.setStatus("RESERVED");
        inventoryEvent.setMessage("Inventory reserved successfully");
      } else {
        // Configura el evento como fallido
        inventoryEvent.setStatus("FAILED");
        inventoryEvent.setMessage("Inventory not available");
      }

      // Adjunta los detalles de la orden al evento
      inventoryEvent.setOrder(orderEvent.getOrder());
      LOGGER.info("Publishing Inventory Event: {}", inventoryEvent);

      // Publica el evento en Kafka
      kafkaTemplate.send(inventoryTopic, inventoryEvent);
    } catch (Exception e) {
      LOGGER.error("Error processing order: {}", e.getMessage(), e);

      // Manejo de errores si ocurre algo inesperado
      inventoryEvent.setStatus("ERROR");
      inventoryEvent.setMessage("An error occurred while processing the order");
      inventoryEvent.setOrder(orderEvent.getOrder());
      kafkaTemplate.send(inventoryTopic, inventoryEvent);
    }
  }

  @Override
  public Optional<Inventory> findByProductName(String productName) {
    return inventoryRepository.findByProductName(productName);
  }

  @Override
  public Inventory save(Inventory inventory) {
    return inventoryRepository.save(inventory);
  }
}
