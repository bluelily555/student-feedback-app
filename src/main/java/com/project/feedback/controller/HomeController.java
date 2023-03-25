package com.project.feedback.controller;

import com.project.feedback.domain.entity.UserEntity;
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
    @GetMapping(value = {"", "/"})
    public String root(Authentication auth, Model model) {
        if(auth != null){
            UserEntity userInfo = findService.findUserByUserName(auth.getPrincipal().toString());
            boolean CheckAdmin = findService.checkAdmin(userInfo);
            if(CheckAdmin){
                return "admin/dummy_objects";
            }else{
                return "redirect:/users/my";
            }
        }else{
            return "redirect:/users/my";
        }
    }
    @GetMapping(value = {"/home"})
    public String home(Authentication auth, Model model){
        return "/users/my";
    }

}
