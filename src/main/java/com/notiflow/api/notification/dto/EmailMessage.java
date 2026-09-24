package com.notiflow.api.notification.dto;

public record EmailMessage(
        String subject,
        String body
) {
}
