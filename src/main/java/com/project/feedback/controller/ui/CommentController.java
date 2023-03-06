package com.project.feedback.controller.ui;

import com.project.feedback.domain.entity.CommentEntity;
import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.domain.enums.LikeContentType;
import com.project.feedback.service.FindService;
import com.project.feedback.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final FindService findService;
    private final LikeService likeService;

    @PostMapping("/{commentId}/like")
    public String likeComment(@PathVariable Long commentId, Authentication auth) {
        // 로그인하지 않은 경우
        CommentEntity comment = findService.findByCommentId(commentId);
        if (auth == null) return "redirect:/boards/" + comment.getBoardEntity().getId();

        UserEntity loginUser = findService.findUserByUserName(auth.getName());

        likeService.like(LikeContentType.COMMENT, commentId, loginUser);

        return "redirect:/boards/" + comment.getBoardEntity().getId();
    }

    @PostMapping("/{commentId}/unlike")
    public String unlikeComment(@PathVariable Long commentId, Authentication auth) {
        // 로그인하지 않은 경우
        CommentEntity comment = findService.findByCommentId(commentId);
        if (auth == null) return "redirect:/boards/" + comment.getBoardEntity().getId();

        UserEntity loginUser = findService.findUserByUserName(auth.getName());

        likeService.unlike(LikeContentType.COMMENT, commentId, loginUser);

        return "redirect:/boards/" + comment.getBoardEntity().getId();
    }
}
