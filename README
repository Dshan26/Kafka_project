# Kafka Microservices Project

## Overview
This project demonstrates a system of microservices communicating asynchronously using Apache Kafka. The system handles order creation, inventory management, and customer notifications. It showcases how to design and implement event-driven architectures with Kafka, ensuring scalability and decoupled communication between services.

## Features
- **Order Management:** A microservice to create orders and publish events to Kafka.
- **Inventory Management:** A microservice that consumes order events, processes inventory updates, and publishes inventory status events.
- **Notification Service:** A microservice that consumes inventory status events and sends email notifications to customers.
- **Kafka Integration:** Centralized communication via Kafka topics (`order_topic` and `inventory_topic`).

## Architecture
### Microservices:
1. **Order Service:**
   - **Role:** Producer.
   - **Publishes to Kafka Topic:** `order_topic`.
   - **Endpoint:** `POST /api/v1/orders` to create a new order.

2. **Inventory Service:**
   - **Roles:** Consumer and Producer.
   - **Consumes from Kafka Topic:** `order_topic`.
   - **Publishes to Kafka Topic:** `inventory_topic`.
   - **Processes:** Updates inventory and determines if the order can be fulfilled.

3. **Notification Service:**
   - **Role:** Consumer.
   - **Consumes from Kafka Topic:** `inventory_topic`.
   - **Processes:** Sends email notifications to customers based on inventory status.

### Kafka Topics:
- **`order_topic`:** Used for communicating new orders from the Order Service to the Inventory Service.
- **`inventory_topic`:** Used for communicating inventory status (e.g., `RESERVED`, `FAILED`) from the Inventory Service to the Notification Service.

### Flow:
1. **Order Service** publishes a new order to `order_topic`.
2. **Inventory Service** consumes the order event from `order_topic`, processes the inventory, and publishes the result to `inventory_topic`.
3. **Notification Service** consumes the inventory status from `inventory_topic` and sends email notifications to the customer.

## Technology Stack
- **Java** (Spring Boot) for microservices.
- **Apache Kafka** for event streaming.
- **PostgreSQL** for inventory database.
- **Spring Mail** for email notifications.

## Prerequisites
1. **Kafka Setup:** Ensure Kafka and Zookeeper are running locally.
   - Download Kafka: [Apache Kafka Downloads](https://kafka.apache.org/downloads)
   - Start Zookeeper:
     ```bash
     ./bin/zookeeper-server-start.sh config/zookeeper.properties
     ```
   - Start Kafka:
     ```bash
     ./bin/kafka-server-start.sh config/server.properties
     ```

2. **Database Setup:**
   - Create a PostgreSQL database named `inventorydb`.
   - Configure connection settings in the `application.properties` file of the Inventory Service.

## How to Run
### Steps:
1. Clone the repository:
   ```bash
   git clone https://github.com/your-repository/kafka-microservices.git
   ```

2. Navigate to each microservice directory and build the project:
   ```bash
   mvn clean install
   ```

3. Start the services:
   - Order Service:
     ```bash
     mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8090"
     ```
   - Inventory Service:
     ```bash
     mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8002"
     ```
   - Notification Service:
     ```bash
     mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8080"
     ```

4. Test the system:
   - Use Postman or any HTTP client to create an order:
     ```
     POST http://localhost:8090/api/v1/orders
     Body:
     {
       "productName": "Laptop",
       "quantity": 2,
       "price": 1200.0
     }
     ```
   - Check logs for Inventory and Notification services to verify processing and email notifications.

## Configuration
### Kafka:
- Topics are auto-created via Spring Boot configuration.
- Example configuration in `application.properties`:
  ```properties
  spring.kafka.consumer.bootstrap-servers=localhost:9092
  spring.kafka.consumer.group-id=inventory-group
  spring.kafka.consumer.auto-offset-reset=earliest
  spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
  spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer
  ```

### Email Notifications:
- Gmail SMTP configuration:
  ```properties
  spring.mail.host=smtp.gmail.com
  spring.mail.port=587
  spring.mail.username=your-email@gmail.com
  spring.mail.password=your-app-password
  spring.mail.properties.mail.smtp.auth=true
  spring.mail.properties.mail.smtp.starttls.enable=true
  ```

## Future Enhancements
- Add a **Payment Service** to handle transactions.
- Integrate a **Shipping Service** to manage order deliveries.
- Implement **Monitoring and Metrics** using Spring Actuator and Prometheus.

## Contributing
Contributions are welcome! Feel free to fork the repository and submit a pull request.

## License
This project is licensed under the MIT License.

---

**Happy Coding!** 🎉

