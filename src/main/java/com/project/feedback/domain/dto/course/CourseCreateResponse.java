package com.project.feedback.domain.dto.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CourseCreateResponse {
    private String message;
    private Long courseId;
    private String name;

    public static CourseCreateResponse of(Long courseId, String name) {
        return CourseCreateResponse.builder()
                .message("기수 등록 완료")
                .name(name)
                .courseId(courseId)
                .build();
    }
}
