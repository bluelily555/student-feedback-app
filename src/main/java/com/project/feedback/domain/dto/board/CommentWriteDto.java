package com.project.feedback.domain.dto.board;

import com.project.feedback.domain.entity.CommentEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentWriteDto {
    private Long id;
    private String writer;
    private String content;
    private Long boardId;
    private String userName;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public CommentEntity toEntity(){
        CommentEntity commentEntity = CommentEntity.builder()
                .writer(writer)
                .content(content)
                .boardId(boardId)
                .userName(userName)
                .build();
        return commentEntity;
    }
    @Builder
    public CommentWriteDto(Long id,Long boardId, String content, String writer,String userName, LocalDateTime createdDate, LocalDateTime modifiedDate){
        this.id = id;
        this.writer = writer;
        this.content = content;
        this.boardId = boardId;
        this.userName = userName;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
