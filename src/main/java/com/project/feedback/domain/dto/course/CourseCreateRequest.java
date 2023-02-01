package com.project.feedback.domain.dto.course;

import com.project.feedback.domain.CourseStatus;
import com.project.feedback.domain.entity.CourseEntity;
import com.project.feedback.domain.entity.UserEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CourseCreateRequest {
    private String name;

    private String description;

    private String status;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;


    public CourseEntity toEntity(UserEntity user) {
        List<UserEntity> users = new ArrayList<>();
        users.add(user);
        return CourseEntity.builder()
                .name(this.name)
                .description(this.description)
                .courseStatus(CourseStatus.valueOf(this.status))
                .startDate(this.startDate)
                .user(user)
                .endDate(this.endDate)
                .build();
    }
}
