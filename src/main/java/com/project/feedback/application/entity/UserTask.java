package com.project.feedback.application.entity;

import com.project.feedback.domain.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserTask {
    private long id;
    private long userId;
    private long taskId;
    private TaskStatus taskStatus;
}
