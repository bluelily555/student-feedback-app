package com.project.feedback.domain.dto.board;


import com.project.feedback.infra.outgoing.jpa.BoardEntity;
import com.project.feedback.infra.outgoing.jpa.ImageEntity;
import com.project.feedback.infra.outgoing.jpa.TaskEntity;
import com.project.feedback.infra.outgoing.jpa.UserEntity;
import lombok.*;


import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardListDto {

    private Long id;
    private TaskEntity taskEntity;
    private String userName;
    private String content;
    private String codeContent;
    private String title;
    private List<String> images;
    private int likes;
    private int comments;
    private String language;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public BoardEntity toEntity(TaskEntity taskEntity, UserEntity user){
        BoardEntity boardEntity = BoardEntity.builder()
                .taskEntity(taskEntity)
                .content(content)
                .language(language)
                .user(user)
                .codeContent(codeContent)
                .title(title)
                .build();
        return boardEntity;
    }

    public static BoardListDto of(BoardEntity boardEntity) {
        return BoardListDto.builder()
            .id(boardEntity.getId())
            .title(boardEntity.getTitle())
            .language(boardEntity.getLanguage())
            .content(boardEntity.getContent())
            .userName(boardEntity.getUser().getUserName())
            .createdDate(boardEntity.getCreatedDate())
            .modifiedDate(boardEntity.getModifiedDate())
            .build();
    }

    public static BoardListDto shortOf(BoardEntity boardEntity) {
        return BoardListDto.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .userName(boardEntity.getUser().getUserName())
                .comments(boardEntity.getComments().size())
                .createdDate(boardEntity.getCreatedDate())
                .build();
    }

    public static BoardListDto detailOf(BoardEntity boardEntity) {
        return BoardListDto.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .taskEntity(boardEntity.getTaskEntity())
                .language(boardEntity.getLanguage())
                .content(boardEntity.getContent())
                .codeContent(boardEntity.getCodeContent())
                .userName(boardEntity.getUser().getRealName())
                .images(boardEntity.getImages().stream().map(ImageEntity::getName).toList())
                .createdDate(boardEntity.getCreatedDate())
                .build();
    }
}
