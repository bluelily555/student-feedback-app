package com.project.feedback.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@Getter
@Entity
@Table(name ="code")
public class CodeEntity extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_entity_id")
    private TaskEntity taskEntity;

    @OneToMany(mappedBy = "codeEntity", orphanRemoval = true)
    private List<CommentsEntity> comments;


    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String codeContent;

    @Column(length = 32, nullable = false)
    private String writer;

    @Column(length = 32, nullable = false)
    private String title;

    @Column(nullable = false)
    private String userName;

    @Builder
    public CodeEntity(Long id, String content, String title, String writer, String userName, String codeContent, TaskEntity taskEntity){
        this.id = id;
        this.taskEntity = taskEntity;
        this.writer = writer;
        this.content = content;
        this.title = title;
        this.userName = userName;
        this.codeContent = codeContent;
    }
}
