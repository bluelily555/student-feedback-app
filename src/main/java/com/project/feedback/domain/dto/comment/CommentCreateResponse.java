package com.project.feedback.domain.dto.comment;

import com.project.feedback.domain.entity.CommentEntity;
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
    private Long boardId;
    private LocalDateTime createdAt;

    public static CommentCreateResponse of(CommentEntity comment) {
        return CommentCreateResponse.builder()
            .id(comment.getId())
            .comment(comment.getComment())
            .userName(comment.getUser().getUserName())
            .boardId(comment.getBoardEntity().getId())
            .createdAt(comment.getCreatedDate())
            .build();
    }
}

