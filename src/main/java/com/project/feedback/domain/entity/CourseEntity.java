package com.project.feedback.domain.entity;

import com.project.feedback.domain.CourseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
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

    //기수 이름
    private String name;

    //설명
    private String description;

    @Enumerated(EnumType.STRING)
    private CourseStatus courseStatus;

    //시작일
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    //종료일
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    @OneToMany(mappedBy = "courseEntity")
    private List<TaskEntity> taskEntities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(targetEntity = User.class)
    private List<User> users;

}
