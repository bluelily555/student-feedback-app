package com.project.feedback.service;

import com.project.feedback.domain.dto.board.CommentWriteDto;
import com.project.feedback.domain.entity.CommentEntity;
import com.project.feedback.repository.BoardRepository;
import com.project.feedback.repository.CommentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.BDDMockito.given;

class CommentServiceTest {

    private CommentRepository commentRepository = mock(CommentRepository.class);

    private CommentService commentService;

    @BeforeEach
    void setUp(){
        this.commentService = new CommentService(commentRepository);
    }


    @Test
    @DisplayName("comment 쓰기 성공")
    void commentWrite() {
        CommentWriteDto commentWriteDto = CommentWriteDto.builder()
                .writer("kyeongrok")
                .content("댓글입니다.")
                .build();

        CommentEntity commentEntity = CommentEntity.builder()
                .id(1l)
                .writer("kyeongrok")
                .boardId(1l)
                .build();

        given(commentRepository.save(any()))
                .willReturn(commentEntity);

        // reference type Long, primitive type - long
        Long commentId = commentService.saveComment(commentWriteDto, 1l);

        Assertions.assertEquals(1l, commentId);

    }
}