package com.project.feedback.controller.ui;

import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.service.FindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminUiController {

    private final FindService findService;

    @GetMapping(value = {"", "/"})
    public String root(Authentication auth, Model model) {
        if(auth != null){
            UserEntity userInfo = findService.findUserByUserName(auth.getPrincipal().toString());
            boolean CheckAdmin = findService.checkAdmin(userInfo);
            if(CheckAdmin){
                return "admin/main";
            }else{
                return "home";
            }
        }else{
            return "home";
        }
    }
}
