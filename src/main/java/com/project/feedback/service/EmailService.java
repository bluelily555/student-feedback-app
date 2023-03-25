package com.project.feedback.service;

import com.project.feedback.domain.dto.user.EmailAuthResponse;

public interface EmailService {
    EmailAuthResponse sendSimpleMessage(String to)throws Exception;
}
