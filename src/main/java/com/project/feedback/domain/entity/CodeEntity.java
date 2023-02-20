package com.project.feedback.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
@Entity
@Table(name ="code")
public class CodeEntity extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="task_id")
    private TaskEntity taskId;
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
    public CodeEntity(Long id, String content, String title, String writer, String userName, String codeContent,TaskEntity taskId){
        this.id = id;
        this.taskId = taskId;
        this.writer = writer;
        this.content = content;
        this.title = title;
        this.userName = userName;
        this.codeContent = codeContent;
    }
}
