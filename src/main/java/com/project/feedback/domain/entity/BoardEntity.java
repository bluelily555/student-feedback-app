package com.project.feedback.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
@Table(name ="board")
public class BoardEntity extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_entity_id")
    private TaskEntity taskEntity;

    @OneToMany(mappedBy = "boardEntity", orphanRemoval = true)
    private List<CommentEntity> comments;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String codeContent;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String language;

    @Column(length = 32, nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
