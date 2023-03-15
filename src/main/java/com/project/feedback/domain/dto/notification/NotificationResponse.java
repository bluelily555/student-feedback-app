package com.project.feedback.domain.dto.notification;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.feedback.domain.entity.NotificationEntity;
import com.project.feedback.domain.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NotificationResponse {
    private Long id;
    private NotificationType type;
    private String from;
    private boolean confirmed;
    private String sourceTitle;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime createdDate;

    public static NotificationResponse of(NotificationEntity entity, String sourceTitle) {
        return NotificationResponse.builder()
                .id(entity.getId())
                .type(entity.getType())
                .from(entity.getFromUser().getUserName())
                .confirmed(entity.isConfirmed())
                .sourceTitle(sourceTitle)
                .createdDate(entity.getCreatedDate())
                .build();
    }
}
