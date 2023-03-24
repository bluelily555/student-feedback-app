package com.project.feedback.domain.enums;

import java.util.List;

public enum NotificationType {
    LIKE_BOARD("%s님이 게시물에 좋아요를 눌렀습니다."),
    LIKE_COMMENT("%s님이 댓글에 좋아요를 눌렀습니다."),
    COMMENT("%s님이 댓글을 남겼습니다.");

    private final String messageFormat;

    public final static List<NotificationType> SOURCE_IS_BOARD = List.of(LIKE_BOARD, LIKE_COMMENT, COMMENT);

    NotificationType(String messageFormat) {
        this.messageFormat = messageFormat;
    }

    public String getMessageFormat() {
        return messageFormat;
    }
}
