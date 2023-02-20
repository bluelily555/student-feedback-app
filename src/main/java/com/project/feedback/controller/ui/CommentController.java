package com.project.feedback.controller.ui;

import com.project.feedback.domain.dto.comment.CommentCreateRequest;
import com.project.feedback.service.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentsService commentService;

    @PostMapping("/{postId}")
    public String write(@PathVariable Long postId, @ModelAttribute CommentCreateRequest req,
                        Authentication auth, Model model) {
            commentService.saveComment(req, auth.getName(), postId);
        return "redirect:/boards/code/view_all";
    }


}
