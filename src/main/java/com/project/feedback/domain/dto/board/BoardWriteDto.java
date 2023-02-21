package com.project.feedback.domain.dto.board;


import com.project.feedback.domain.entity.BoardEntity;
import com.project.feedback.domain.entity.TaskEntity;
import com.project.feedback.domain.entity.UserEntity;
import lombok.*;


import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardWriteDto {

    private Long id;
    private TaskEntity taskEntity;
    private String userName;
    private String content;
    private String codeContent;
    private String title;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public BoardEntity toEntity(TaskEntity taskEntity, UserEntity user){
        BoardEntity boardEntity = BoardEntity.builder()
                .taskEntity(taskEntity)
                .content(content)
                .user(user)
                .codeContent(codeContent)
                .title(title)
                .build();
        return boardEntity;
    }

    public static BoardWriteDto of(BoardEntity boardEntity) {
        return BoardWriteDto.builder()
            .id(boardEntity.getId())
            .title(boardEntity.getTitle())
            .content(boardEntity.getContent())
            .userName(boardEntity.getUser().getUserName())
            .createdDate(boardEntity.getCreatedDate())
            .modifiedDate(boardEntity.getModifiedDate())
            .build();
    }
}
