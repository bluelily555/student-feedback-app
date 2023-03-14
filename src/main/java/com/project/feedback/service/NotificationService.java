package com.project.feedback.service;

import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.domain.enums.NotificationType;
import com.project.feedback.observer.notification.event.NotificationCreateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * 알림 생성. <br>
     * 알림을 받는 유저와 발생시킨 유저가 같으면 알림을 생성하지 않음.
     * @param type 알림 타입.
     * @param sourceId 알림 발생 원인을 확인하기 위한 소스 id.
     * @param targetUser 알림을 받는 유저.
     * @param fromUser 알림을 발생시킨 유저.
     */
    @Async("notificationEventExecutor")
    public void create(NotificationType type, Long sourceId, UserEntity targetUser, UserEntity fromUser) {
        if (targetUser.equals(fromUser)) return;
        log.info("{}", String.format(type.getMessageFormat(), fromUser.getUserName()));
        applicationEventPublisher.publishEvent(NotificationCreateEvent.of(type, sourceId, targetUser, fromUser));
    }
}
