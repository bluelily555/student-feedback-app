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
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminUiController {

    private final FindService findService;
    private final UserService userService;

    @GetMapping(value = {"", "/"})
    public String root(Authentication auth, Model model) {
        return "redirect:admin/dummy";
    }

    @GetMapping(value = {"/dummy"})
    public String dummy(Authentication auth, Model model){
        model.addAttribute("currentPage", "dummy");
        model.addAttribute("courses", findService.findAllCourse());
        return "admin/dummy_objects";
    }


    //Dummy User 등록
    @GetMapping("/users")
    public String addDummyUsers(@RequestParam(name = "userCnt", defaultValue = "0") int userCnt){
        log.info("userCnt:{}", userCnt);
        userService.addDummyUsers(userCnt);
        return "redirect:/users";
    }

    //Dummy Task 등록
    @GetMapping("/tasks")
    public String addDummyTasks(@RequestParam(name = "taskCnt", defaultValue = "0") int taskCnt,
                                @RequestParam(name = "questionCnt", defaultValue = "0") int questionCnt,
                                @RequestParam(name = "courseId") long courseId){
        log.info("courseId:{}", courseId);
        log.info("taskCnt:{}", taskCnt);
        log.info("questionCnt:{}", questionCnt);
        userService.addDummyTasks(courseId, taskCnt, questionCnt);
        return "redirect:/tasks";
    }

}
