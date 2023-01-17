package com.project.feedback.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CourseEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //기수 이름
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