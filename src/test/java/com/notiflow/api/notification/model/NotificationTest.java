package com.notiflow.api.notification.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class NotificationTest {

    @Test
    void shouldCreateNotificationAsPending() {
        Notification notification = createNotification();

        assertAll(
                () -> assertEquals(NotificationStatus.PENDING, notification.getStatus()),
                () -> assertNull(notification.getSentAt()),
                () -> assertNull(notification.getFailureReason())
        );
    }

    @Test
    void shouldMarkNotificationAsSent() {
        Notification notification = createNotification();

        notification.markAsSent();

        assertAll(
                () -> assertEquals(NotificationStatus.SENT, notification.getStatus()),
                () -> assertNotNull(notification.getSentAt()),
                () -> assertNull(notification.getFailureReason())
        );
    }

    @Test
    void shouldMarkNotificationAsFailed() {
        Notification notification = createNotification();

        notification.markAsFailed(NotificationFailureReason.DELIVERY_FAILED);

        assertAll(
                () -> assertEquals(NotificationStatus.FAILED, notification.getStatus()),
                () -> assertEquals(
                        NotificationFailureReason.DELIVERY_FAILED,
                        notification.getFailureReason()
                ),
                () -> assertNull(notification.getSentAt())
        );
    }

    @Test
    void shouldClearFailureReasonWhenMarkedAsSentAfterFailure() {
        Notification notification = createNotification();

        notification.markAsFailed(NotificationFailureReason.DELIVERY_FAILED);
        notification.markAsSent();

        assertAll(
                () -> assertEquals(NotificationStatus.SENT, notification.getStatus()),
                () -> assertNotNull(notification.getSentAt()),
                () -> assertNull(notification.getFailureReason())
        );
    }

    private Notification createNotification() {
        return new Notification(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "Implement notification flow",
                "Task description",
                null,
                UUID.randomUUID(),
                "member@notiflow.com",
                "Manager",
                "Member",
                NotificationType.TASK_ASSIGNED
        );
    }
}