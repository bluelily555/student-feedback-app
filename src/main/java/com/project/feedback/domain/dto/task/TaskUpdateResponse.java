package com.project.feedback.domain.dto.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TaskUpdateResponse {
    private String message;
    private Long taskId;

    public static TaskUpdateResponse of(Long taskId) {
        return TaskUpdateResponse.builder()
                .message("태스크 수정 완료")
                .taskId(taskId)
                .build();
    }
}
