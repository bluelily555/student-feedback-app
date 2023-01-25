package com.project.feedback.controller.api;


import com.project.feedback.domain.Response;
import com.project.feedback.domain.dto.course.AddStudentRequest;
import com.project.feedback.domain.dto.course.CourseCreateRequest;
import com.project.feedback.domain.dto.course.CourseCreateResponse;
import com.project.feedback.domain.dto.mainInfo.CourseTaskListResponse;
import com.project.feedback.domain.entity.User;
import com.project.feedback.service.CourseService;
import com.project.feedback.service.FindService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseApiController {

    private final CourseService courseService;
    private final FindService findService;

    @PostMapping
    public Response<CourseCreateResponse> write(@RequestBody CourseCreateRequest req, Authentication auth) {
        CourseCreateResponse res = courseService.createCourse(req, auth.getName());
        return Response.success(res);
    }

    @PostMapping("/register")
    public String registerStudent(@RequestBody AddStudentRequest studentRequest) {
        courseService.registerStudent(studentRequest);
        return "success";
    }

    // 기수에 속한 학생 목록 output = 학생 이름, 진행 상황 status
    @GetMapping("/{courseId}/students")
    public String getStudents(@PathVariable Long courseId, Authentication auth) {
        User loginUser = findService.findUserByUserName(auth.getName());
        List<User> users = findService.findUserByCourseId(courseId, loginUser);
        //학생 각각의 task에 대한 진행상황
        return "success";
    }


    @GetMapping("/{courseId}/tasks")
    public Response<CourseTaskListResponse> getTasks(@PathVariable Long courseId, Authentication auth) {
        User loginUser = findService.findUserByUserName(auth.getName());
        CourseTaskListResponse res = findService.getTasksAndStudents(courseId, loginUser);
        return Response.success(res);
    }

    @GetMapping("/{courseId}/tasks/weeks/{week}/days/{day}")
    public Response<CourseTaskListResponse> getTasksByFilter(@PathVariable Long courseId,
                                                     @PathVariable Long week,
                                                     @PathVariable Long day,
                                                     Authentication auth) {
        User loginUser = findService.findUserByUserName(auth.getName());
        CourseTaskListResponse res = findService.getTasksAndStudentsByWeekAndDay(courseId, week, day, loginUser);
        return Response.success(res);
    }

}