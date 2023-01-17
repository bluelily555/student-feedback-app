package com.project.feedback.controller.ui;

import com.project.feedback.domain.dto.course.CourseCreateRequest;
import com.project.feedback.service.CourseService;
import com.project.feedback.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/courses")
@RequiredArgsConstructor
@Slf4j
public class CourseController {
    private final TaskService taskService;
    private final CourseService courseService;

    @GetMapping("/write")
    public String writePage(Model model) {
        model.addAttribute("courseCreateRequest", new CourseCreateRequest());
        return "tasks/write";
    }

    @PostMapping("/write")
    public String write(@ModelAttribute CourseCreateRequest req, Model model, Authentication auth) {
        courseService.createCourse(req);
        return "redirect:/";
    }

    @GetMapping("/{courseId}")
    public String show(@PathVariable Long courseId, Model model, Authentication auth) {
//        CourseDto res = courseService.getOneCourse(courseId);
//        model.addAttribute("courseDetail", res);
//
//        // 화면에 changeable을 true로 넘겨주면 수정, 삭제 가능
//        User taskUser = findService.findUserByUserName(res.getUserName());
//        User loginUser = findService.findUserByUserName(auth.getName());
//        if(auth != null && findService.checkAuth(taskUser, loginUser)) {
//            model.addAttribute("changeable", true);
//        } else {
//            model.addAttribute("changeable", false);
//        }
//
        return "tasks/show";
    }


}
