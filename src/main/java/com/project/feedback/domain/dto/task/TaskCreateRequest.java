package com.project.feedback.domain.dto.task;

import com.project.feedback.domain.TaskStatus;
import com.project.feedback.infra.outgoing.entity.CourseEntity;
import com.project.feedback.infra.outgoing.entity.TaskEntity;
import com.project.feedback.infra.outgoing.entity.UserEntity;
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
