package com.project.feedback.controller.ui;

import com.project.feedback.domain.dto.repository.RepositoryRequest;
import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.service.FindService;
import com.project.feedback.service.RepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/repositories")
public class RepositoryController {
    private final FindService findService;
    private final RepositoryService repositoryService;

    @PostMapping()
    public String save(RepositoryRequest request, Authentication auth) {
        UserEntity loginUser = findService.findUserByUserName(auth.getName());
        repositoryService.save(request, loginUser);
        return "redirect:users/my";
    }
}
