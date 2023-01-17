package com.project.feedback.domain.dto.task;

import com.project.feedback.domain.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskUpdateRequest {
    private String title;
    private String description;
    private TaskStatus taskStatus;
    private Integer week;
    private Integer day;
}
