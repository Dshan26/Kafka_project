package com.sandoval.inventory.cms.controller;

import com.sandoval.inventory.cms.service.InventoryService;
import com.sandoval.model.cms.model.entities.Inventory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {


  private final InventoryService inventoryService;

  public InventoryController(InventoryService inventoryService) {
    this.inventoryService = inventoryService;
  }


  /**
   * Consulta el stock de un producto por su nombre.
   *
   * @param productName Nombre del producto.
   * @return Detalles del inventario o un mensaje de error si no existe.
   */
  @GetMapping("/{productName}")
  public ResponseEntity<?> getProductStock(@PathVariable String productName) {
    Optional<Inventory> inventoryOpt = inventoryService.findByProductName(productName);

    if (inventoryOpt.isPresent()) {
      return ResponseEntity.ok(inventoryOpt.get());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found in inventory");
    }
  }

  /**
   * Agrega o actualiza un producto en el inventario.
   *
   * @param inventory Objeto del producto.
   * @return Producto guardado o mensaje de error.
   */
  @PostMapping("/create")
  public ResponseEntity<?> addOrUpdateProduct(@RequestBody Inventory inventory) {
    if (inventory.getProductName() == null || inventory.getQuantity() < 0) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid product details");
    }
    Inventory savedInventory = inventoryService.save(inventory);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedInventory);
  }
}