package com.project.feedback.domain.dto.mainInfo;

import com.project.feedback.domain.entity.TaskEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TaskInfo {
    private Long id;
    private String title;
    private Long week;
    private Long day;

    public static TaskInfo of(TaskEntity task) {
        return TaskInfo.builder()
            .id(task.getId())
            .title(task.getTitle())
            .week(task.getWeek())
            .day(task.getDay())
            .build();
    }

}