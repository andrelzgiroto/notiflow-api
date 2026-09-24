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

The current version is a monolithic Spring Boot application using internal Spring events to decouple task operations from notification processing.

```text
TaskService ──▶ Spring Event ──▶ NotificationEventHandler ──▶ NotificationService ──▶ EmailSender ──▶ SMTP
```

`TaskService` no longer triggers notifications directly. Events represent domain facts such as task assignment and completion and carry the data required by the notification flow, allowing the notification layer to react without directly depending on the task domain.

Event handling is still synchronous and runs within the same application, establishing the event-driven flow before external messaging and asynchronous processing are introduced.

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

### Events and Notifications

- Spring Application Events
- Spring Mail
- SMTP

### Tools

- Maven

## Project Direction

Notiflow evolves incrementally from an event-driven monolith toward more decoupled and resilient processing.

Future stages will explore external messaging, asynchronous consumers, reliability mechanisms, and service separation as these needs emerge from the architecture.

The domain remains intentionally small so the focus stays on technical decisions and system evolution.