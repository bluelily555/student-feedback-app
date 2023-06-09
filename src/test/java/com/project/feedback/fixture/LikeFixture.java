package com.project.feedback.fixture;

import com.project.feedback.infra.outgoing.jpa.LikeEntity;
import com.project.feedback.infra.outgoing.jpa.UserEntity;
import com.project.feedback.domain.enums.LikeContentType;

public class LikeFixture {

    public static LikeEntity boardLikeEntity(Long boardId, UserEntity from) {
        return LikeEntity.of(LikeContentType.BOARD, boardId, from);
    }

    public static LikeEntity commentLikeEntity(Long commentId, UserEntity from) {
        return LikeEntity.of(LikeContentType.COMMENT, commentId, from);
    }

    public static LikeEntity boardLikeEntity_좋아요(Long boardId, UserEntity from) {
        return LikeEntity.builder()
                .contentType(LikeContentType.BOARD)
                .contentId(boardId)
                .fromUser(from)
                .status(true)
                .build();
    }
}
