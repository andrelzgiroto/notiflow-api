package com.notiflow.api.notification.service;

import com.notiflow.api.notification.dto.EmailMessage;
import com.notiflow.api.notification.dto.NotificationRequest;
import com.notiflow.api.notification.email.EmailSender;
import com.notiflow.api.notification.mapper.NotificationMapper;
import com.notiflow.api.notification.model.Notification;
import com.notiflow.api.notification.model.NotificationFailureReason;
import com.notiflow.api.notification.model.NotificationType;
import com.notiflow.api.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationService {

    private final EmailSender emailSender;
    private final NotificationRepository notificationRepository;

    @Transactional
    public void process(NotificationRequest notificationRequest) {
        Notification notification =
                notificationRepository.save(NotificationMapper.toEntity(notificationRequest));

        EmailMessage emailMessage = buildEmailMessage(notification, notification.getType());

        try {
            emailSender.send(
                    notification.getRecipientEmail(),
                    emailMessage.subject(),
                    emailMessage.body()
            );

            notification.markAsSent();
            log.info("Notification sent successfully: notificationId={}", notification.getId());
        }
        catch (MailAuthenticationException ex) {
            notification.markAsFailed(NotificationFailureReason.AUTHENTICATION_FAILED);

            log.error(
                    "Email authentication failed for notificationId={}",
                    notification.getId(),
                    ex
            );
        }
        catch (MailSendException ex) {
            notification.markAsFailed(NotificationFailureReason.DELIVERY_FAILED);

            log.error(
                    "Email delivery failed for notificationId={}",
                    notification.getId(),
                    ex
            );
        }
        catch (MailException ex) {
            notification.markAsFailed(NotificationFailureReason.UNKNOWN_ERROR);

            log.error(
                    "Unexpected mail error for notificationId={}",
                    notification.getId(),
                    ex
            );
        }
    }

    private EmailMessage buildEmailMessage(Notification notification, NotificationType notificationType) {
        String subject = "";
        String body = "";

        if (notificationType == NotificationType.TASK_ASSIGNED) {
            subject = "New task assigned: " + notification.getTaskTitle();
            body = """
            Hello %s,
            
            You have been assigned a new task by %s.
            
            Task: %s
            Description: %s
            """
            .formatted(
                    notification.getAssignedToName(),
                    notification.getAssignedByName(),
                    notification.getTaskTitle(),
                    notification.getTaskDescription()
            );
        }

        if (notificationType == NotificationType.TASK_COMPLETED) {
            subject = "Task completed: " + notification.getTaskTitle();

            body = """
            Hello %s,

            The task "%s" has been completed by %s.

            Completed at: %s
            """.formatted(
                    notification.getAssignedByName(),
                    notification.getTaskTitle(),
                    notification.getAssignedToName(),
                    notification.getTaskFinishedAt()
            );
        }

        return new EmailMessage(subject, body);
    }
}

