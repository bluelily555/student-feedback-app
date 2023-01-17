package com.project.feedback.domain.dto.task;

import com.project.feedback.domain.TaskStatus;
import com.project.feedback.domain.entity.CourseEntity;
import com.project.feedback.domain.entity.TaskEntity;
import com.project.feedback.domain.entity.User;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskCreateRequest {
    private String title;
    private String description;
    private String status;
    private String courseName;
    private Integer week;
    private Integer day;
    public TaskEntity toEntity(User user, CourseEntity course) {
        return TaskEntity.builder()
                .title(this.title)
                .description(this.description)
                .week(this.week)
                .day(this.day)
                .taskStatus(TaskStatus.valueOf(this.status))
                .courseEntity(course)
                .user(user)
                .build();
    }
}
