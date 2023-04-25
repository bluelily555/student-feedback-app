package com.project.feedback.application;

import com.project.feedback.domain.dto.user.EmailAuthResponse;

public interface EmailService {
    EmailAuthResponse sendSimpleMessage(String to)throws Exception;
}
