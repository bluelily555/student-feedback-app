package com.project.feedback.domain.entity;

import jakarta.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
@Entity
@Table(name = "board")
public class BoardEntity extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(length = 32, nullable = false)
    private String writer;
    @Column(nullable = false)
    private String userId;

    @Builder
    public BoardEntity(Long id, String title, String content, String writer, String userId){
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

}
