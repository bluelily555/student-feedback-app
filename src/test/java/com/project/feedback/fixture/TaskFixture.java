package com.project.feedback.fixture;

import com.project.feedback.domain.dto.task.TaskCreateRequest;
import com.project.feedback.domain.dto.task.TaskCreateResponse;
import com.project.feedback.infra.outgoing.jpa.TaskEntity;

public class TaskFixture {
    public static TaskCreateRequest taskCreateRequest(String status) {
        return TaskCreateRequest.builder()
                .title("Task 제목")
                .status(status)
                .build();
    }

    public static TaskEntity taskEntity(Long id) {
        return TaskEntity.builder()
                .id(id)
                .build();
    }

    public static TaskCreateResponse taskCreateResponse(String title) {
        return TaskCreateResponse.builder()
                .taskId(1l)
                .title(title)
                .build();
    }

}
