package com.project.feedback.service;

import com.project.feedback.domain.dto.comment.CommentCreateRequest;
import com.project.feedback.domain.dto.comment.CommentCreateResponse;
import com.project.feedback.domain.entity.BoardEntity;
import com.project.feedback.domain.entity.CommentEntity;
import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.repository.CommentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.BDDMockito.given;

class CommentServiceTest {

    private CommentRepository commentRepository = mock(CommentRepository.class);

    private CommentService commentService;

    private FindService findService = mock(FindService.class);

    @BeforeEach
    void setUp(){
        this.commentService = new CommentService(commentRepository, findService);
    }


    @Test
    @DisplayName("comment 쓰기 성공")
    void commentWrite() {
        UserEntity user = UserEntity.builder()
            .userName("kyeongrok")
            .build();

        BoardEntity boardEntity = BoardEntity.builder()
            .id(1l)
            .build();

        CommentCreateRequest commentCreateRequest = CommentCreateRequest.builder()
                .comment("댓글입니다.")
                .build();

        CommentEntity commentEntity = CommentEntity.builder()
                .id(1l)
                .comment("댓글입니다.")
                .user(user)
                .boardEntity(boardEntity)
                .build();

        given(commentRepository.save(any()))
                .willReturn(commentEntity);

        // reference type Long, primitive type - long
        CommentCreateResponse result = commentService.saveComment(commentCreateRequest, "kyeongrok",1l);

        Assertions.assertEquals(1l, result.getBoardId());

    }
}