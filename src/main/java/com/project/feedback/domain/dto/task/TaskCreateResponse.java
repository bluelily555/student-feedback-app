package com.project.feedback.domain.dto.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TaskCreateResponse {
    private String message;
    private Long taskId;

    public static TaskCreateResponse of(Long taskId) {
        return TaskCreateResponse.builder()
                .message("task 등록 완료")
                .taskId(taskId)
                .build();
    }
}
