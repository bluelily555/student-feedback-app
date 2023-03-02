package com.project.feedback.fixture;

import com.project.feedback.domain.entity.LikeEntity;
import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.domain.enums.LikeContentType;

public class LikeFixture {

    public static LikeEntity boardLikeEntity(UserEntity from, Long boardId) {
        return LikeEntity.of(LikeContentType.BOARD, from, boardId);
    }

    public static LikeEntity commentLikeEntity(UserEntity from, Long boardId) {
        return LikeEntity.of(LikeContentType.COMMENT, from, boardId);
    }
}
