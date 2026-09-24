# Notiflow

Notiflow is a backend project built as a practical environment for exploring architectural evolution through a simple notification domain.

Users assign, receive, and complete tasks, while relevant actions trigger email notifications. The domain remains intentionally small so the focus stays on technical decisions and system evolution.

## Purpose

The project explores how a simple backend flow evolves as new architectural concerns appear.

The main areas of exploration are:
- event-driven communication;
- asynchronous processing and messaging;
- reliability and failure handling;
- gradual evolution toward distributed systems.

The goal is to understand the impact of each architectural change instead of introducing complexity without a clear reason.

## Main Flow

```text
MANAGER  ── assigns task ─────▶ MEMBER   ── receives email notification
MEMBER   ── completes task ───▶ MANAGER  ── receives email notification
```

## Current Architecture

The current version is a monolithic Spring Boot application using Apache Kafka to decouple task operations from notification processing.

```text
TaskService
    │
    ▼
TaskEventProducer
    │
    ▼
Kafka ── task-events
    │
    ▼
NotificationConsumer
    │
    ▼
NotificationService
    │
    ▼
EmailSender ──▶ SMTP
```

`TaskService` publishes events representing domain facts such as task assignment and completion. These events carry the data required by the notification flow and are published to Kafka without directly invoking the notification layer.

`NotificationConsumer` consumes the events asynchronously and delegates notification processing to `NotificationService`.

This separates task operations from notification delivery while keeping both domains inside the same application, preparing the project for further reliability improvements and eventual service separation.

## Technologies

### Backend

- Java 21
- Spring Boot
- Spring Web
- Bean Validation

### Persistence

- Spring Data JPA
- Hibernate
- PostgreSQL

### Messaging and Notifications

- Apache Kafka
- Spring Kafka
- Spring Mail
- SMTP

### Tools

- Maven
- Docker
- Docker Compose

## Project Direction

Notiflow evolves incrementally from a simple backend flow toward more decoupled and resilient processing.

The current architecture introduces asynchronous communication through Kafka while remaining a monolithic application.

Future stages will explore reliability mechanisms such as retry, idempotency and failure handling, followed by gradual service separation as these needs emerge from the architecture.

The domain remains intentionally small so the focus stays on technical decisions and system evolution.