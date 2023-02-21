package com.project.feedback.fixture;

import com.project.feedback.domain.dto.comment.CommentCreateRequest;

public class CommentDtoFixture {
    public static CommentCreateRequest commentCreateRequest(String comment) {

        CommentCreateRequest commentCreateRequest = CommentCreateRequest.builder()
                .comment(comment)
                .build();

        return commentCreateRequest;
    }
}
