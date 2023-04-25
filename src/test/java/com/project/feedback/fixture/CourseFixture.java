package com.project.feedback.fixture;

import com.project.feedback.infra.outgoing.jpa.CourseEntity;

public class CourseFixture {
    public static CourseEntity courseEntity() {
        return CourseEntity.builder()
                .name("BP 백엔드 1기")
                .build();
    }
}
