package com.sandoval.model.cms.model.repository;

import com.sandoval.model.cms.model.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
  Optional<Inventory> findByProductName(String productName);
}
