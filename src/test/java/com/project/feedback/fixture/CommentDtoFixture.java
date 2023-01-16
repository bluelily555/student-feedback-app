package com.project.feedback.fixture;

import com.project.feedback.domain.dto.board.CommentWriteDto;

public class CommentDtoFixture {
    public static CommentWriteDto commentWriteDto(Long boardId, String content) {

        CommentWriteDto commentWriteDto = CommentWriteDto.builder()
                .writer("kyeongrok")
                .content(content)
                .boardId(boardId)
                .build();

        return commentWriteDto;
    }
}
