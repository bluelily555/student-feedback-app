package com.project.feedback.domain.entity;

import com.project.feedback.domain.Status;
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
    private Status status;

    // 몇주
    private String week;

    // 몇일
    private String day;

    public void update(String newTitle, String newDesc, Status status, String week, String day) {
        this.title = newTitle;
        this.description = newDesc;
        this.status = status;
        this.week = week;
        this.day = day;
    }
}
