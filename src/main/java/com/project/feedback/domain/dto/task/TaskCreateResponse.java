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
    private String title;

    public static TaskCreateResponse of(Long taskId, String title) {
        return TaskCreateResponse.builder()
                .message("task 등록 완료")
                .title(title)
                .taskId(taskId)
                .build();
    }
}
