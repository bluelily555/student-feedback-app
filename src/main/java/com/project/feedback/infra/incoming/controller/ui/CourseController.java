package com.project.feedback.infra.incoming.controller.ui;

import com.project.feedback.application.entity.Course;
import com.project.feedback.domain.dto.course.AddStudentRequest;
import com.project.feedback.domain.dto.course.CourseCreateRequest;
import com.project.feedback.domain.dto.course.CourseInfo;
import com.project.feedback.domain.dto.mainInfo.CourseTaskListResponse;
import com.project.feedback.domain.dto.mainInfo.FilterInfo;
import com.project.feedback.domain.dto.mainInfo.StudentInfo;
import com.project.feedback.domain.dto.mainInfo.TaskInfo;
import com.project.feedback.infra.outgoing.entity.CourseEntity;
import com.project.feedback.infra.outgoing.entity.UserEntity;
import com.project.feedback.application.CourseService;
import com.project.feedback.application.FindService;
import com.project.feedback.application.UserTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Controller
@RequestMapping("/courses")
@RequiredArgsConstructor
@Slf4j
public class CourseController {
    private final CourseService courseService;
    private final FindService findService;
    private final UserTaskService userTaskService;

    @GetMapping
    public String list(Model model, @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        List<Course> courses = courseService.getCourses(pageable);

        model.addAttribute("currentPage", "courses");
        model.addAttribute("courses", courses);
        model.addAttribute("nowPage", pageable.getPageNumber() + 1);
        model.addAttribute("lastPage", pageable.getPageSize());

        return "courses/show";
    }

    @GetMapping("/write")
    public String writePage(Model model) {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String dateNow  = now.format(formatter);
        int cnt = courseService.getCourseLength()+1;
//        String cnt = String.valueOf(courseService.getCourseLength()+1);
        model.addAttribute("currentPage", "create_course");
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


    // course 수정 화면
    @GetMapping("/{courseId}")
    public String show(@PathVariable Long courseId, Model model, Authentication auth,
                       @PageableDefault(size = 20)
                       @SortDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        model.addAttribute("course", courseService.findByCourseId2(courseId));

        return "courses/edit";
    }


    @PostMapping("/register")
    public String register(@ModelAttribute("addStudentRequest") AddStudentRequest req, Authentication auth) {
        courseService.registerStudent(req);
        return  "redirect:/users";
    }


    @GetMapping("/students")
    public String addCourseId(Authentication auth, RedirectAttributes redirectAttributes, Model model) {
        log.info(SecurityContextHolder.getContext().getAuthentication().getName());
        UserEntity loginUser = findService.findUserByUserName(auth.getName());
        CourseEntity course = findService.findCourseByUserId(loginUser);
        CourseInfo courseInfo = CourseInfo.fromEntity(course);
//        Long userId = findService.findUserByUserName(auth.getName()).getId();
//        List<UserTaskEntity> userTaskEntityList = userTaskService.getAllTaskByUserId(userId);

        FilterInfo filterInfo = FilterInfo.builder()
            .day(Long.valueOf(courseInfo.getWeek()))
            .week(Long.valueOf(courseInfo.getDayOfWeek()))
            .build();
        
        redirectAttributes.addAttribute("week", courseInfo.getWeek());
        redirectAttributes.addAttribute("day", courseInfo.getDayOfWeek());
        model.addAttribute("filterInfo", filterInfo);
//        model.addAttribute("taskList", userTaskEntityList);
        redirectAttributes.addAttribute("courseId", course.getId());


        return "redirect:{courseId}/students/weeks/{week}/days/{day}";
    }

    @PostMapping("/students")
    public String weekAndDaySubmit(@ModelAttribute FilterInfo filterInfo, Model model, RedirectAttributes redirectAttribute, Authentication auth) {
        UserEntity loginUser = findService.findUserByUserName(auth.getName());
        CourseEntity course = findService.findCourseByUserId(loginUser);
        redirectAttribute.addAttribute("courseId", course.getId());
        redirectAttribute.addAttribute("week", filterInfo.getWeek());
        redirectAttribute.addAttribute("day", filterInfo.getDay());
        return "redirect:{courseId}/students/weeks/{week}/days/{day}";
    }

    @GetMapping("/{courseId}/students/weeks/{week}/days/{day}")
    public String listWeekAndDay(@PathVariable long courseId, @PathVariable long week, @PathVariable long day, Authentication auth, Model model){
        UserEntity loginUser = findService.findUserByUserName(auth.getName());
        CourseEntity course = findService.findCourseByUserId(loginUser);
        List<StudentInfo> result = findService.getStudentsWithTask(courseId,  week, day, loginUser);
        CourseTaskListResponse res =  findService.getTasksAndStudentsByWeekAndDay(courseId, week, day, loginUser);
        Long userId = findService.findUserByUserName(auth.getName()).getId();
//        List<UserTaskEntity> userTaskEntityList = userTaskService.getAllTaskByUserId(userId);

        List<TaskInfo> taskList = res.getTaskInfoList();

        model.addAttribute("filterInfo", new FilterInfo());
        model.addAttribute("taskList", taskList);
        model.addAttribute("studentTaskList", result);
        model.addAttribute("courseId", courseId);
        model.addAttribute("courseName", course.getName());
        model.addAttribute("authName", auth.getName());
        model.addAttribute("week", week);
        model.addAttribute("day", day);
//        model.addAttribute("userTaskList", userTaskEntityList);

        return "courses/students/show";
    }

    @ResponseBody
    @PostMapping("/setWeek")
    public Long calculateWeek(@RequestParam String courseName){
        CourseEntity courseEntity = findService.findCourseByName(courseName);
        Long week = CourseInfo.fromEntity(courseEntity).getWeek();
        return week;
    }


}
