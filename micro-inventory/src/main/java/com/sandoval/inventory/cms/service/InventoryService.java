package com.sandoval.inventory.cms.service;

import com.sandoval.model.cms.model.Order;
import com.sandoval.model.cms.model.OrderEvent;
import com.sandoval.model.cms.model.entities.Inventory;

import java.util.Optional;


public interface InventoryService {
  void processOrder(OrderEvent order);

  Optional<Inventory> findByProductName(String productName);

  Inventory save(Inventory inventory);
}
