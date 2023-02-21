package com.project.feedback.domain.dto.comment;
import com.project.feedback.domain.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class CommentListDto {

    private Long id;
    private String comment;
    private String userName;
    private Long codeId;
    private LocalDateTime createdAt;

    public static CommentListDto of(CommentEntity comment) {
        return CommentListDto.builder()
            .id(comment.getId())
            .comment(comment.getComment())
            .userName(comment.getUser().getUserName())
            .codeId(comment.getBoardEntity().getId())
            .createdAt(comment.getCreatedDate())
            .build();
    }
}
