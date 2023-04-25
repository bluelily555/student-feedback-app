package com.project.feedback.infra.incoming.controller.ui;

import com.project.feedback.domain.dto.repository.RepositoryRequest;
import com.project.feedback.infra.outgoing.jpa.UserEntity;
import com.project.feedback.application.FindService;
import com.project.feedback.application.RepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
        return "redirect:/users/my";
    }

    @PostMapping("/{repositoryId}/edit")
    public String edit(@PathVariable Long repositoryId, RepositoryRequest request, Authentication auth) {
        UserEntity loginUser = findService.findUserByUserName(auth.getName());
        repositoryService.update(repositoryId, request, loginUser);
        return "redirect:/users/my";
    }

    @PostMapping("/{repositoryId}/delete")
    public String delete(@PathVariable Long repositoryId, Authentication auth) {
        UserEntity loginUser = findService.findUserByUserName(auth.getName());
        repositoryService.delete(repositoryId, loginUser);
        return "redirect:/users/my";
    }
}
