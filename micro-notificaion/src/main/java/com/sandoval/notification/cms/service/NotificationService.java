package com.sandoval.notification.cms.service;

import com.sandoval.model.cms.model.InventoryEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

  private static final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);

  private final EmailService emailService;

  public NotificationService(EmailService emailService) {
    this.emailService = emailService;
  }

  public void sendNotification(InventoryEvent inventoryEvent) {
    String toEmail = "sandovalcamilo496@gmail.com";

    if ("RESERVED".equalsIgnoreCase(inventoryEvent.getStatus())) {
      LOGGER.info("Notification: Order {} has been processed successfully.", inventoryEvent.getOrder().getOrderId());
      emailService.sendEmail(
              toEmail,
              "Order Confirmation",
              String.format("Your order %s has been successfully processed. Product: %s, Quantity: %d.",
                      inventoryEvent.getOrder().getOrderId(),
                      inventoryEvent.getOrder().getProductName(),
                      inventoryEvent.getOrder().getQuantity())
      );
    } else if ("FAILED".equalsIgnoreCase(inventoryEvent.getStatus())) {
      LOGGER.warn("Notification: Order {} could not be processed. Reason: {}", inventoryEvent.getOrder().getOrderId(), inventoryEvent.getMessage());
      emailService.sendEmail(
              toEmail,
              "Order Failure Notification",
              String.format("We regret to inform you that your order %s could not be processed. Reason: %s.",
                      inventoryEvent.getOrder().getOrderId(),
                      inventoryEvent.getMessage())
      );
    } else {
      LOGGER.error("Notification: Unhandled event status: {}", inventoryEvent.getStatus());
    }
  }
}
