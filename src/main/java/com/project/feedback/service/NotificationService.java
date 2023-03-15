package com.project.feedback.service;

import com.project.feedback.domain.dto.notification.NotificationResponse;
import com.project.feedback.domain.entity.BoardEntity;
import com.project.feedback.domain.entity.NotificationEntity;
import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.domain.enums.NotificationType;
import com.project.feedback.exception.CustomException;
import com.project.feedback.exception.ErrorCode;
import com.project.feedback.observer.notification.event.NotificationCreateEvent;
import com.project.feedback.repository.BoardRepository;
import com.project.feedback.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final BoardRepository boardRepository;
    private final NotificationRepository notificationRepository;

    /**
     * 알림 생성. <br>
     * 알림을 받는 유저와 발생시킨 유저가 같으면 알림을 생성하지 않음.
     *
     * @param type       알림 타입.
     * @param sourceId   알림 발생 원인을 확인하기 위한 소스 id.
     * @param targetUser 알림을 받는 유저.
     * @param fromUser   알림을 발생시킨 유저.
     */
    @Async("notificationEventExecutor")
    public void create(NotificationType type, Long sourceId, UserEntity targetUser, UserEntity fromUser) {
        if (targetUser.equals(fromUser)) return;
        log.info("{}", String.format(type.getMessageFormat(), fromUser.getUserName()));
        applicationEventPublisher.publishEvent(NotificationCreateEvent.of(type, sourceId, targetUser, fromUser));
    }

    public List<NotificationResponse> findByTargetUser(UserEntity targetUser) {
        return notificationRepository.findByTargetUserFetch(targetUser).stream()
                .map(notification -> {
                    String sourceTitle = null;

                    switch (notification.getType()) {
                        case LIKE_BOARD, LIKE_COMMENT, COMMENT -> {
                            Optional<BoardEntity> boardEntityOptional = boardRepository.findById(notification.getSourceId());
                            if (boardEntityOptional.isPresent()) {
                                sourceTitle = boardEntityOptional.get().getTitle();
                            }
                        }
                    }

                    return NotificationResponse.of(notification, sourceTitle);
                }).toList();
    }

    @Transactional
    public String confirm(Long notificationId, UserEntity loginUser) {
        NotificationEntity notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOTIFICATION_NOT_FOUND));

        if (!notification.equalsOwner(loginUser)) throw new CustomException(ErrorCode.INVALID_PERMISSION);

        notification.confirm();

        switch (notification.getType()) {
            case LIKE_BOARD, LIKE_COMMENT, COMMENT:
                return "/boards/" + notification.getSourceId();

            default:
                return null;
        }
    }
}
