package com.project.feedback.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "course")
public class CourseEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //코스 이름
    private String name;

    //시작일
    private LocalDate startDate;

    //종료일
    private LocalDate endDate;

    @OneToMany(mappedBy = "courseEntity")
    private List<TaskEntity> taskEntities;

    @OneToMany(mappedBy = "courseEntity")
    private List<User> users;

}
