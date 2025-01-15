package com.sandoval.model.cms.model;

import lombok.Data;

@Data
public class InventoryEvent {
  private String status; // RESERVED o FAILED
  private String message;
  private Order order;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }
}