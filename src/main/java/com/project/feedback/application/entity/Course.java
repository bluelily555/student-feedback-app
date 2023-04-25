package com.project.feedback.application.entity;

import com.project.feedback.domain.CourseStatus;
import com.project.feedback.infra.outgoing.jpa.CourseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Course {
    private Long id;
    //기수 이름
    private String name;
    //설명
    private String description;

    private CourseStatus courseStatus;

    //시작일
    private LocalDate startDate;

    //종료일
    private LocalDate endDate;

    public static Course toCourse(CourseEntity courseEntity) {
        Course course = Course.builder()
                .id(courseEntity.getId())
                .name(courseEntity.getName())
                .description(courseEntity.getDescription())
                .courseStatus(courseEntity.getCourseStatus())
                .startDate(courseEntity.getStartDate())
                .endDate(courseEntity.getEndDate())
                .build();
        return course;
    }

}
