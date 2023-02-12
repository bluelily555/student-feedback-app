package com.project.feedback.domain.entity;

import com.project.feedback.domain.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_task")
public class UserTaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 하나의 USER가 가질 수 있는 userTask는 여러개
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_entity_id")
    private TaskEntity taskEntity;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;
}
