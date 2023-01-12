package com.project.feedback.domain.dto.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TaskDeleteResponse {
    private String message;
    private Long taskId;

    public static TaskDeleteResponse of(Long taskId) {
        return TaskDeleteResponse.builder()
                .message("task 삭제 완료")
                .taskId(taskId)
                .build();
    }
}
