package com.project.feedback.domain.enums;

import lombok.Getter;

@Getter
public enum CacheType {
    GET_STUDENT("get_students_with_task", 5, 10000),
    GET_TASKS_AND_STUDENTS("get_tasks_and_students", 5, 10000),
    COURSE_STUDENT("course_student", 5 * 60, 10000);


    CacheType(String cacheName, int expiredAfterWrite, int maximumSize) {
        this.cacheName = cacheName;
        this.expiredAfterWrite = expiredAfterWrite;
        this.maximumSize = maximumSize;
    }

    private String cacheName;
    private int expiredAfterWrite;
    private int maximumSize;
}
