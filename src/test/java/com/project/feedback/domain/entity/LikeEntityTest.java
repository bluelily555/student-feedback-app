package com.project.feedback.domain.entity;

import com.project.feedback.fixture.LikeFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LikeEntityTest {

    @Test
    void initialize() {
        UserEntity user = UserEntity.builder().build();
        LikeEntity boardLikeEntity = LikeFixture.boardLikeEntity(1L, user);
        LikeEntity commentLikeEntity = LikeFixture.commentLikeEntity(1L, user);

        assertFalse(boardLikeEntity.isStatus());
        assertFalse(commentLikeEntity.isStatus());
    }

    @Test
    void like_unlike() {
        UserEntity user = UserEntity.builder().build();
        LikeEntity boardLikeEntity = LikeFixture.boardLikeEntity(1L, user);
        LikeEntity commentLikeEntity = LikeFixture.commentLikeEntity(1L, user);

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