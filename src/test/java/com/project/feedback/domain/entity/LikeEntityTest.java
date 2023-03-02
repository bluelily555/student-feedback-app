package com.project.feedback.domain.entity;

import com.project.feedback.fixture.LikeFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LikeEntityTest {

    @Test
    void initialize() {
        UserEntity user = UserEntity.builder().build();
        LikeEntity boardLikeEntity = LikeFixture.boardLikeEntity(user, 1L);
        LikeEntity commentLikeEntity = LikeFixture.commentLikeEntity(user, 1L);

        assertFalse(boardLikeEntity.isStatus());
        assertFalse(commentLikeEntity.isStatus());
    }

    @Test
    void like_unlike() {
        UserEntity user = UserEntity.builder().build();
        LikeEntity boardLikeEntity = LikeFixture.boardLikeEntity(user, 1L);
        LikeEntity commentLikeEntity = LikeFixture.commentLikeEntity(user, 1L);

        boardLikeEntity.like();
        commentLikeEntity.like();

        assertTrue(boardLikeEntity.isStatus());
        assertTrue(commentLikeEntity.isStatus());

        boardLikeEntity.unlike();
        commentLikeEntity.unlike();

        assertFalse(boardLikeEntity.isStatus());
        assertFalse(commentLikeEntity.isStatus());
    }
}