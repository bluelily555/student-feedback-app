package com.project.feedback.domain.entity;

import com.project.feedback.domain.CourseStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "course")
public class CourseEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //기수 이름
    @Column(unique = true)
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

    //기수 만든 사람
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "courseEntity")
    private List<CourseEntityUser> courseEntityUsers;

}
