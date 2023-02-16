package com.project.feedback.domain.dto.task;

import com.project.feedback.domain.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskUpdateRequest {
    private String title;
    private String description;
    private TaskStatus taskStatus;
    private Long week;
    private Long day;
}
