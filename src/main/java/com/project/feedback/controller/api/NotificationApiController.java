package com.project.feedback.controller.api;

import com.project.feedback.domain.dto.notification.NotificationResponse;
import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.exception.CustomException;
import com.project.feedback.exception.ErrorCode;
import com.project.feedback.service.FindService;
import com.project.feedback.service.NotificationService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@Api(tags = {"알림(Notification)"})
public class NotificationApiController {
    private final FindService findService;
    private final NotificationService notificationService;

    @GetMapping()
    public List<NotificationResponse> getMyNotification(Authentication auth) {
        if (auth == null) throw new CustomException(ErrorCode.INVALID_PERMISSION);

        UserEntity loginUser = findService.findUserByUserName(auth.getName());

        return notificationService.findByTargetUser(loginUser);
    }
}
