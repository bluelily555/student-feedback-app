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
        return "courses/write";
    }

    @PostMapping("/write")
    public String write(@ModelAttribute CourseCreateRequest req, Authentication auth) {
        courseService.createCourse(req, auth.getName());
        return "redirect:/";
    }

}