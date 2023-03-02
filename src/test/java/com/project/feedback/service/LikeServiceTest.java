package com.project.feedback.service;

import com.project.feedback.domain.entity.LikeEntity;
import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.domain.enums.LikeContentType;
import com.project.feedback.fixture.LikeFixture;
import com.project.feedback.repository.LikeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class LikeServiceTest {
    private final LikeRepository likeRepository = Mockito.mock(LikeRepository.class);
    private final LikeService likeService = new LikeService(likeRepository);

    @Test
    @DisplayName("처음으로 좋아요를 누르는 경우")
    void like_first_time() {
        // given
        LikeContentType type = LikeContentType.BOARD;
        Long boardId = 1L;
        UserEntity from = UserEntity.builder().build();
        given(likeRepository.findByContentTypeAndContentIdAndFromUser(eq(type), eq(boardId), any(UserEntity.class)))
                .willReturn(Optional.empty());
        given(likeRepository.save(any(LikeEntity.class))).willReturn(LikeFixture.boardLikeEntity(boardId, from));

        // when
        // then
        assertDoesNotThrow(() -> likeService.like(type, boardId, from));
        verify(likeRepository).findByContentTypeAndContentIdAndFromUser(eq(type), eq(1L), any(UserEntity.class));
        verify(likeRepository).save(any(LikeEntity.class));
    }

    @Test
    @DisplayName("좋아요를 누르는 것이 처음이 아닌 경우")
    void like_exists_row() {
        // given
        LikeContentType type = LikeContentType.BOARD;
        Long boardId = 1L;
        UserEntity from = UserEntity.builder().build();
        given(likeRepository.findByContentTypeAndContentIdAndFromUser(eq(type), eq(boardId), any(UserEntity.class)))
                .willReturn(Optional.of(LikeFixture.boardLikeEntity(boardId, from)));

        // when
        // then
        assertDoesNotThrow(() -> likeService.like(type, boardId, from));
        verify(likeRepository).findByContentTypeAndContentIdAndFromUser(eq(type), eq(1L), any(UserEntity.class));
        verify(likeRepository, never()).save(any(LikeEntity.class));
    }

    @Test
    @DisplayName("좋아요 취소 정상")
    void unlike() {
        LikeContentType type = LikeContentType.BOARD;
        Long boardId = 1L;
        UserEntity from = UserEntity.builder().build();
        given(likeRepository.findByContentTypeAndContentIdAndFromUser(eq(type), eq(boardId), any(UserEntity.class)))
                .willReturn(Optional.of(LikeFixture.boardLikeEntity_좋아요(boardId, from)));

        // when
        // then
        assertDoesNotThrow(() -> likeService.unlike(type, boardId, from));
        verify(likeRepository).findByContentTypeAndContentIdAndFromUser(eq(type), eq(1L), any(UserEntity.class));
    }

    @Test
    @DisplayName("좋아요를 누르지 않고 취소하는 경우")
    void unlike_not_found_like_history() {
        LikeContentType type = LikeContentType.BOARD;
        Long boardId = 1L;
        UserEntity from = UserEntity.builder().build();
        given(likeRepository.findByContentTypeAndContentIdAndFromUser(eq(type), eq(boardId), any(UserEntity.class)))
                .willReturn(Optional.empty());

        // when
        // then
        assertDoesNotThrow(() -> likeService.unlike(type, boardId, from));
        verify(likeRepository).findByContentTypeAndContentIdAndFromUser(eq(type), eq(1L), any(UserEntity.class));
    }
}