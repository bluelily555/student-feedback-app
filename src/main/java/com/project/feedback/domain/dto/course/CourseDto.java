package com.project.feedback.domain.dto.course;

import com.project.feedback.infra.outgoing.jpa.CourseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class CourseDto {
    private Long id;
    private String name;

    public static CourseDto fromEntity(CourseEntity entity) {
        return new CourseDto(entity.getId(), entity.getName());
    }
}
