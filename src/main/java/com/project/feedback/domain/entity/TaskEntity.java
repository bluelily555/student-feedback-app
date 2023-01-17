package com.project.feedback.domain.entity;

import com.project.feedback.domain.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private CourseEntity courseEntity;

    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    // 몇주
    private Integer week;

    // 몇일
    private Integer day;

    public void update(String newTitle, String newDesc, TaskStatus taskStatus, Integer week, Integer day) {
        this.title = newTitle;
        this.description = newDesc;
        this.taskStatus = taskStatus;
        this.week = week;
        this.day = day;
    }
}
