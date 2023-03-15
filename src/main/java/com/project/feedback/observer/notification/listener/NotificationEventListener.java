package com.project.feedback.observer.notification.listener;

import com.project.feedback.observer.notification.event.NotificationCreateEvent;
import com.project.feedback.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {
    private final NotificationRepository notificationRepository;

    @EventListener
    public void create(NotificationCreateEvent event) {
        notificationRepository.save(event.getNotification());
    }
}
