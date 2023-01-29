package com.project.feedback.domain.entity;

import jakarta.persistence.*;
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

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(length = 32, nullable = false)
    private String writer;

    @Column(length = 32, nullable = false)
    private String title;
    @Column(nullable = false)
    private String userName;

    @Builder
    public CodeEntity(Long id, String content, String title, String writer, String userName){
        this.id = id;
        this.writer = writer;
        this.content = content;
        this.title = title;
        this.userName = userName;
    }
}
