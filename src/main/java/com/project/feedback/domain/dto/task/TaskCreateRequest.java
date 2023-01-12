package com.project.feedback.domain.dto.task;

import com.project.feedback.domain.Role;
import com.project.feedback.domain.Status;
import com.project.feedback.domain.entity.TaskEntity;
import com.project.feedback.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskCreateRequest {
    private String title;
    private String description;

    public TaskEntity toEntity(User user) {
        return TaskEntity.builder()
                .title(this.title)
                .description(this.description)
                .status(Status.SIGNUP)
                .user(user)
                .build();
    }
}
