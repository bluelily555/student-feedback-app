package com.project.feedback.infra.incoming.controller.api;

import com.project.feedback.infra.outgoing.jpa.UserEntity;
import com.project.feedback.application.BoardService;
import com.project.feedback.application.FindService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/boards")
@RequiredArgsConstructor
@Api(tags = {"질문(Board)"})
public class BoardApiController {
    private final FindService findService;
    private final BoardService boardService;

    @Operation(summary = "질문 삭제")
    @DeleteMapping("/{id}")
    public Long delete(@PathVariable Long id, Authentication auth) {
        UserEntity loginUser = findService.findUserByUserName(auth.getName());
        boardService.delete(id, loginUser);
        return id;
    }
}
