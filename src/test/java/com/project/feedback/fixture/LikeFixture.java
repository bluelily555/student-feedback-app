package com.project.feedback.fixture;

import com.project.feedback.domain.entity.LikeEntity;
import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.domain.enums.LikeContentType;

public class LikeFixture {

    public static LikeEntity boardLikeEntity(Long boardId, UserEntity from) {
        return LikeEntity.of(LikeContentType.BOARD, boardId, from);
    }

    public static LikeEntity commentLikeEntity(Long commentId, UserEntity from) {
        return LikeEntity.of(LikeContentType.COMMENT, commentId, from);
    }
}
