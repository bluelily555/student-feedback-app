package com.project.feedback.controller.ui;

import com.project.feedback.domain.dto.board.BoardCreateRequest;
import com.project.feedback.domain.entity.TaskEntity;
import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.service.FindService;
import com.project.feedback.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminUiController {

    private final FindService findService;
    private final UserService userService;

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

    //Dummy User 등록
    @GetMapping("/users")
    public String addDummyUsers(){
        userService.addDummyUsers();
        return "admin/main";
    }

    //Dummy User 등록
    @PostMapping("/tasks")
    public String addDummyTasks(){

        return "";
    }

}
