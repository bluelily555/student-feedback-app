package com.project.feedback.domain.entity;

import com.project.feedback.domain.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


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

    //task를 작성한 User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private CourseEntity courseEntity;

    @OneToMany(mappedBy = "taskEntity")
    private List<UserTask> userTasks;

    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    // 몇주
    private Long week;

    // 몇일
    private Long day;

    public void update(String newTitle, String newDesc, TaskStatus taskStatus, Long week, Long day) {
        this.title = newTitle;
        this.description = newDesc;
        this.taskStatus = taskStatus;
        this.week = week;
        this.day = day;
    }
}
