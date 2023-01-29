package com.project.feedback.domain.dto.board;

import com.project.feedback.domain.entity.BoardEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardWriteDto {
    private Long id;
    private String writer;
    private String title;
    private String content;
    private String userName;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    public BoardEntity toEntity(){
        BoardEntity boardEntity = BoardEntity.builder()
                .id(id)
                .writer(writer)
                .title(title)
                .content(content)
                .userName(userName)
                .build();
        return boardEntity;
    }

    @Builder
    public BoardWriteDto(Long id, String title, String content, String writer, String userName, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.userName = userName;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
