package com.project.feedback.infra.outgoing.jpa;

import com.project.feedback.domain.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE task SET deleted_at = NOW() WHERE id=?")
@Where(clause = "deleted_at is NULL")
@Table(name = "task")
public class TaskEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    //task를 작성한 User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private CourseEntity courseEntity;

    @OneToMany(mappedBy = "taskEntity")
    private List<UserTaskEntity> userTaskEntities;

    //글 목록
    @OneToMany(mappedBy = "taskEntity")
    private List<BoardEntity> boardEntities;

    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    //삭제 날짜
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // 몇주
    private Long week;

    // 몇일
    private Long dayOfWeek;

    public void update(String newTitle,CourseEntity courseEntity, String newDesc, String taskStatus, Long week, Long day) {
        this.title = newTitle;
        this.description = newDesc;
        this.taskStatus = TaskStatus.valueOf(taskStatus);
        this.courseEntity = courseEntity;
        this.week = week;
        this.dayOfWeek = day;
    }
}
