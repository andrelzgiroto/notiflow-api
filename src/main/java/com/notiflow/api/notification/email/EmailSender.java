package com.notiflow.api.notification.email;

public interface EmailSender {

    void send(String recipient, String subject, String body);
}
