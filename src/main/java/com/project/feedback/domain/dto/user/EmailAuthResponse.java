package com.project.feedback.domain.dto.user;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailAuthResponse {
    private String requestedEmail;
    private String status;
    private String message;
}
