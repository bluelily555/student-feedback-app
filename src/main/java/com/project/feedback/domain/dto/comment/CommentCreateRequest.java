package com.project.feedback.domain.dto.comment;

import com.project.feedback.domain.entity.CodeEntity;
import com.project.feedback.domain.entity.CommentsEntity;
import com.project.feedback.domain.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateRequest {
    private String comment;

    public CommentsEntity toEntity(UserEntity user, CodeEntity codeEntity) {
        return CommentsEntity.builder()
            .comment(this.comment)
            .user(user)
            .codeEntity(codeEntity)
            .build();
    }
}
