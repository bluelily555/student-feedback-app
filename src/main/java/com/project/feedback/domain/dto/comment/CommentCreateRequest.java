package com.project.feedback.domain.dto.comment;

import com.project.feedback.domain.entity.BoardEntity;
import com.project.feedback.domain.entity.CommentEntity;
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
public class CommentCreateRequest {
    private String comment;

    public CommentEntity toEntity(UserEntity user, BoardEntity boardEntity) {
        return CommentEntity.builder()
            .comment(this.comment)
            .user(user)
            .boardEntity(boardEntity)
            .build();
    }
}
