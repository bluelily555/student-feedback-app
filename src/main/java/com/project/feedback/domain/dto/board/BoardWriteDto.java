package com.project.feedback.domain.dto.board;

import com.project.feedback.domain.dto.mainInfo.StatusInfo;
import com.project.feedback.domain.dto.mainInfo.StudentInfo;
import com.project.feedback.domain.entity.BoardEntity;
import com.project.feedback.domain.entity.UserEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
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

    public static BoardWriteDto of(BoardEntity boardEntity) {
        return BoardWriteDto.builder()
            .id(boardEntity.getId())
            .writer(boardEntity.getWriter())
            .title(boardEntity.getTitle())
            .content(boardEntity.getContent())
            .userName(boardEntity.getUserName())
            .createdDate(boardEntity.getCreatedDate())
            .modifiedDate(boardEntity.getModifiedDate())
            .build();
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
