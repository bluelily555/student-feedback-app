package com.project.feedback.domain.dto.board;

import com.project.feedback.domain.entity.CommentEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommentWriteDto {
    private Long id;
    private String writer;
    private String content;
    private Long boardId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public CommentEntity toEntity(){
        CommentEntity commentEntity = CommentEntity.builder()
                .id(id)
                .writer(writer)
                .content(content)
                .boardId(boardId)
                .build();
        return commentEntity;
    }
    @Builder
    public CommentWriteDto(Long id,Long boardId, String content, String writer,LocalDateTime createdDate, LocalDateTime modifiedDate){
        this.id = id;
        this.writer = writer;
        this.content = content;
        this.boardId = boardId;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
