**# Smart Notification Orchestrator**

A **production-style backend notification system** built with **Spring Boot and RabbitMQ** that processes notifications asynchronously while ensuring **reliability, fault tolerance, and controlled concurrency**.

This project demonstrates how real backend systems handle **message queues, failure isolation, rate limiting, and backpressure** under load.

---

**## Architecture Overview**
Client Request
│
▼
Spring Boot REST API
│
▼
RabbitMQ Exchange
│
▼
notification.queue
│
▼
Notification Consumer
│
▼
Notification Processor
│
▼
Dead Letter Queue (DLQ) on failure


---
**
## Key Features**

### Asynchronous Notification Processing
Requests are accepted instantly by the API and processed asynchronously using RabbitMQ queues.

### RabbitMQ Messaging
Messages are published to an exchange and routed to queues for background processing.

### Manual Message Acknowledgement
Consumers explicitly ACK or NACK messages to ensure reliable delivery.

### Dead Letter Queue (DLQ)
Failed notifications are automatically redirected to a DLQ for inspection and recovery.

### Controlled Concurrency
Consumers run concurrently with tuned **prefetch settings** to prevent worker overload.

### Backpressure Handling
Prefetch configuration ensures each consumer processes only a limited number of messages at a time.

### Rate Limiting
Burst traffic is controlled to protect the system from overload.

### Load Testing
Parallel request bursts were simulated to observe queue behavior and message processing stability.

---

**## Tech Stack**

**Backend Framework**
- Spring Boot

**Messaging System**
- RabbitMQ

**Database**
- PostgreSQL

**ORM**
- Spring Data JPA

**Containerization**
- Docker

**Build Tool**
- Maven

---

**## Project Structure**
src/main/java/com/notification/orchestrator

controller
NotificationController

service
NotificationService
NotificationProcessor

queue
NotificationPublisher
NotificationConsumer

repository
NotificationRepository

entity
Notification
NotificationStatus

config
RabbitMQConfig


---

## Queue Configuration

Main Queue
notification.queue

Dead Letter Queue
notification.dlq

Dead Letter Exchange
notification.dlq.exchange

Routing Key
notification.routingKey

---

**## Message Flow**

1. Client sends notification request to REST API  
2. Notification is saved in the database  
3. Message ID is published to RabbitMQ exchange  
4. Message enters notification queue  
5. Consumer retrieves message  
6. NotificationProcessor processes the request  
7. If processing fails → message is routed to DLQ  

---

**## Example Request**

**POST**
/api/notifications

**Body**

``json
{
  "channel": "EMAIL",
  "recipient": "test@gmail.com",
  "templateId": "WELCOME_TEMPLATE"
}

**Running the Project**
1 Clone the repository
git clone https://github.com/yourusername/smart-notification-orchestrator.git

2 Start infrastructure
docker compose up -d

This starts:
RabbitMQ
PostgreSQL

3 Run the application
mvn spring-boot:run

4 Access RabbitMQ Dashboard
http://localhost:15672

Default credentials:
username: guest
password: guest

**Load Testing Example**
Burst requests were simulated using parallel curl commands.
for i in {1..30}; do
curl -X POST http://localhost:8080/api/notifications \
-H "Content-Type: application/json" \
-d '{"channel":"EMAIL","recipient":"test@gmail.com","templateId":"WELCOME_TEMPLATE"}' &
done

This helps observe:
queue buffering
consumer concurrency
backpressure behavior

**Reliability Mechanisms**
Manual ACK / NACK to ensure reliable message processing
Dead Letter Queue to isolate failed notifications
Prefetch control to limit message overload
Rate limiting to protect the API from burst traffic

**Future Improvements**
Possible extensions to make the system more production-ready:
Retry strategy with exponential backoff
Provider fallback (Email → SMS → Push)
Observability with Prometheus / Grafana
Distributed deployment with Kubernetes
Idempotency handling for duplicate messages


**Author**

**Siddharth Mishra**
Backend developer focused on building scalable and reliable distributed systems.



