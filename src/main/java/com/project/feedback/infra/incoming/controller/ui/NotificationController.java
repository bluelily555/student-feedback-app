package com.project.feedback.infra.incoming.controller.ui;

import com.project.feedback.infra.outgoing.entity.UserEntity;
import com.project.feedback.application.FindService;
import com.project.feedback.application.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {
    private final FindService findService;
    private final NotificationService notificationService;

    @GetMapping("/{notificationId}")
    public String confirmNotification(@PathVariable Long notificationId, Authentication auth) {
        // 로그인하지 않은 경우
        if (auth == null) return "/users/login";

        UserEntity loginUser = findService.findUserByUserName(auth.getName());

        return "redirect:" + notificationService.confirm(notificationId, loginUser);
    }
}
