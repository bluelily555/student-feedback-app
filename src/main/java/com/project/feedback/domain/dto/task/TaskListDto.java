package com.project.feedback.domain.dto.task;

import com.project.feedback.domain.entity.TaskEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class TaskListDto {

    private Long id;
    private String title;
    private String userName;
    private Long week;
    private Long day;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    public static TaskListDto of(TaskEntity task) {
        return TaskListDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .userName(task.getUser().getUserName())
                .week(task.getWeek())
                .day(task.getDay())
                .createdAt(task.getCreatedAt())
                .lastModifiedAt(task.getLastModifiedAt())
                .build();
    }
}
