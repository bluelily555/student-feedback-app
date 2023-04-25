package com.project.feedback.application.entity;

import com.project.feedback.infra.outgoing.jpa.TaskEntity;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private long id;
    private String title;

    public static Task fromEntity(TaskEntity entity) {
        return Task.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .build();
    }
}
