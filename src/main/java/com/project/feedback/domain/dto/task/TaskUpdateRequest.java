package com.project.feedback.domain.dto.task;

import com.project.feedback.domain.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskUpdateRequest {
    private String title;
    private String description;
    private String taskStatus;
    private String courseName;
    private Long week;
    private Long day;
}
