package com.project.feedback.observer.notification.event;

import com.project.feedback.infra.outgoing.entity.NotificationEntity;
import com.project.feedback.infra.outgoing.entity.UserEntity;
import com.project.feedback.domain.enums.NotificationType;


public class NotificationCreateEvent {
    private final NotificationEntity notification;

    private NotificationCreateEvent(NotificationEntity notification) {
        this.notification = notification;
    }

    public NotificationEntity getNotification() {
        return notification;
    }

    public static NotificationCreateEvent of(NotificationType type, Long sourceId, UserEntity targetUser, UserEntity fromUser) {
        return new NotificationCreateEvent(NotificationEntity.builder()
                .type(type)
                .targetUser(targetUser)
                .fromUser(fromUser)
                .sourceId(sourceId)
                .build());
    }
}
