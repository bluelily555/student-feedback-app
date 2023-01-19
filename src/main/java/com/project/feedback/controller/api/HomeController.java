package com.project.feedback.controller.api;

import com.project.feedback.domain.entity.User;
import com.project.feedback.service.FindService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequiredArgsConstructor
@ApiIgnore
public class HomeController {
    private final FindService findService;
    @GetMapping(value = {"", "/", "/home"})
    public String home(Authentication auth, Model model) {
        if(auth != null){
            User userInfo = findService.findUserByUserName(auth.getPrincipal().toString());
            boolean CheckAdmin = findService.checkAdmin(userInfo);
            if(CheckAdmin){
                return "/users/admin";
            }else{
                return "home";
            }
        }else{
            return "home";
        }

    }
}
