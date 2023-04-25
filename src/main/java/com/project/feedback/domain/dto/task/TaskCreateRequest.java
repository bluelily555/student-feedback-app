package com.project.feedback.domain.dto.task;

import com.project.feedback.domain.TaskStatus;
import com.project.feedback.infra.outgoing.jpa.CourseEntity;
import com.project.feedback.infra.outgoing.jpa.TaskEntity;
import com.project.feedback.infra.outgoing.jpa.UserEntity;
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
    private Long week;
    private Long day;

    public TaskEntity toEntity(UserEntity user, CourseEntity course) {
        return TaskEntity.builder()
                .title(this.title)
                .description(this.description)
                .week(this.week)
                .dayOfWeek(this.day)
                .taskStatus(TaskStatus.valueOf(this.status))
                .courseEntity(course)
                .user(user)
                .build();
    }
}
