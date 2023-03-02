package com.project.feedback.domain.dto.board;

import com.project.feedback.domain.entity.BoardEntity;
import com.project.feedback.domain.entity.TaskEntity;
import com.project.feedback.domain.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardCreateRequest {
    private String title;
    private String content;
    private String codeContent;


    public BoardEntity toEntity(UserEntity user, TaskEntity taskEntity) {
        return BoardEntity.builder()
            .title(title)
            .codeContent(codeContent)
            .content(content)
            .user(user)
            .taskEntity(taskEntity)
            .build();
    }
}
