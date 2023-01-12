package com.project.feedback.domain.dto.task;

import com.project.feedback.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskUpdateRequest {
    private String title;
    private String description;
    private Status status;
    private String day;
    private String week;
}
