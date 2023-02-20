package com.project.feedback.domain.dto.comment;

import com.project.feedback.domain.entity.CommentsEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateResponse {
    private Long id;
    private String comment;
    private String userName;
    private Long codeId;
    private LocalDateTime createdAt;

    public static CommentCreateResponse of(CommentsEntity comment) {
        return CommentCreateResponse.builder()
            .id(comment.getId())
            .comment(comment.getComment())
            .userName(comment.getUser().getUserName())
            .codeId(comment.getCodeEntity().getId())
            .createdAt(comment.getCreatedDate())
            .build();
    }
}

