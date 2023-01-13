package com.project.feedback.domain.dto.task;

import com.project.feedback.domain.Status;
import com.project.feedback.domain.entity.CourseEntity;
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
    private String status;
    private String courseName;
    private String week;
    private String day;

    public TaskEntity toEntity(User user, CourseEntity course) {
        return TaskEntity.builder()
                .title(this.title)
                .description(this.description)
                .week(this.week)
                .day(this.day)
                .status(Status.valueOf(status))
                .courseEntity(course)
                .user(user)
                .build();
    }
}
