package com.project.feedback.application.entity;


import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CourseStudent {
    private Course course;
    private List<User> users;
    private List<Task> tasks;



}
