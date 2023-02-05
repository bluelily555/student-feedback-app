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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String dateNow  = now.format(formatter);
        int cnt = courseService.getCourseLength()+1;
//        String cnt = String.valueOf(courseService.getCourseLength()+1);
        model.addAttribute("cnt", cnt);
        model.addAttribute("date", dateNow);
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

    @GetMapping("/students")
    public String addCourseId(Authentication auth, RedirectAttributes redirectAttributes) {

        UserEntity loginUser = findService.findUserByUserName(auth.getName());
        CourseEntity course = findService.findCourseByUserId(loginUser);


        redirectAttributes.addAttribute("courseId", course.getId());

        return "redirect:{courseId}/students";
    }

    @GetMapping("/{courseId}/students")
    public String list(@PathVariable long courseId, Authentication auth, Model model){
        UserEntity loginUser = findService.findUserByUserName(auth.getName());
        CourseEntity course = findService.findCourseByUserId(loginUser);
        List<StudentInfo> result = findService.getStudentsWithTask2(courseId,  1L, 1L, loginUser);
        CourseTaskListResponse res =  findService.getTasksAndStudentsByWeekAndDay(courseId, 1L, 1L, loginUser);
        List<String> taskNames = new ArrayList<>();
        for(int i = 0; i < res.getTaskInfoList().size(); i++){
            taskNames.add(res.getTaskInfoList().get(i).getTitle());
        }
        model.addAttribute("taskList", taskNames);
        model.addAttribute("studentTaskList", result);
        model.addAttribute("courseId", courseId);
        model.addAttribute("courseName", course.getName());

        return "courses/students/show";
    }




}
