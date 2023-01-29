package com.project.feedback.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
@Entity
@Table(name = "comment")
public class CommentEntity extends TimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(length = 32, nullable = false)
    private String writer;

    @Column(length = 32, nullable = false)
    private Long boardId;
    @Column(nullable = false)
    private  String userName;

    @Builder
    public CommentEntity(Long id, String content, String writer, Long boardId, String userName){
        this.id = id;
        this.writer = writer;
        this.content = content;
        this.boardId = boardId;
        this.userName = userName;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }
    public void setId(Long id){this.id = id;}
}
