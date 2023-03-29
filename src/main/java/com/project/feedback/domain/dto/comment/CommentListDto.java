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
    private String userProfile;
    private Long boardId;
    private int likes;
    private boolean likeStatus;
    private LocalDateTime createdAt;

    public void setLikeStatus(boolean status) {
        this.likeStatus = status;
    }

    public static CommentListDto of(CommentEntity comment) {
        return CommentListDto.builder()
            .id(comment.getId())
            .comment(comment.getComment())
            .userName(comment.getUser().getUserName())
            .boardId(comment.getBoardEntity().getId())
            .createdAt(comment.getCreatedDate())
            .build();
    }

    public static CommentListDto of(CommentEntity comment, int likes) {
        return CommentListDto.builder()
            .id(comment.getId())
            .comment(comment.getComment())
            .userName(comment.getUser().getUserName())
            .userProfile(comment.getUser().getProfilePath())
            .likes(likes)
            .boardId(comment.getBoardEntity().getId())
            .createdAt(comment.getCreatedDate())
            .build();
    }
}
