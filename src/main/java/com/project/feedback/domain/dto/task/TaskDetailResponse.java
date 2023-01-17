package com.project.feedback.domain.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.feedback.domain.TaskStatus;
import com.project.feedback.domain.entity.TaskEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class TaskDetailResponse {

    private Long id;
    private String title;
    private String description;
    private TaskStatus taskStatus;
    private Integer week;
    private Integer day;
    private String userName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedAt;

    public static TaskDetailResponse of(TaskEntity task) {
        return TaskDetailResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .week(task.getWeek())
                .day(task.getDay())
                .taskStatus(task.getTaskStatus())
                .userName(task.getUser().getUserName())
                .createdAt(task.getCreatedAt())
                .lastModifiedAt(task.getLastModifiedAt())
                .build();
    }
}
