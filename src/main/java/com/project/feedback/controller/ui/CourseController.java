package com.project.feedback.controller.ui;

import com.project.feedback.domain.dto.course.AddStudentRequest;
import com.project.feedback.domain.dto.course.CourseCreateRequest;
import com.project.feedback.domain.dto.mainInfo.CourseTaskListResponse;
import com.project.feedback.domain.dto.mainInfo.StudentInfo;
import com.project.feedback.domain.entity.CourseEntity;
import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.service.CourseService;
import com.project.feedback.service.FindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/courses")
@RequiredArgsConstructor
@Slf4j
public class CourseController {
    private final CourseService courseService;
    private final FindService findService;

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

    @GetMapping("/register")
    public String register(Model model) {
        List<Long> users = new ArrayList<>();
        model.addAttribute("addStudentRequest", new AddStudentRequest());
        return  "users/show";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("addStudentRequest") AddStudentRequest req, Authentication auth) {
        courseService.registerStudent(req);
        return  "redirect:/users";
    }

   // @GetMapping("/{courseId}/students/weeks/{week}/days/{day}")
    @GetMapping("/students")
    public String list(
                       Model model,
                      Authentication auth) {

        UserEntity loginUser = findService.findUserByUserName(auth.getName());
        CourseEntity course = findService.findCourseByUserId(loginUser);

       // List<HashMap<String, String>> result = findService.getStudentsWithTask(1L, 1L, 1L, loginUser);
        List<StudentInfo> result = findService.getStudentsWithTask2(1L, 1L, 1L, loginUser);
        CourseTaskListResponse res =  findService.getTasksAndStudentsByWeekAndDay(1L, 1L, 1L, loginUser);
        List<String> taskNames = new ArrayList<>();
        for(int i = 0; i < res.getTaskInfoList().size(); i++){
            taskNames.add(res.getTaskInfoList().get(i).getTitle());
        }


        model.addAttribute("taskList", taskNames);
        model.addAttribute("studentTaskList", result);
        model.addAttribute("courseId", course.getId());
        model.addAttribute("courseName", course.getName());

        return "courses/students/show";
    }


}
