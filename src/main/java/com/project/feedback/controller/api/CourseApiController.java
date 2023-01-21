package com.project.feedback.controller.api;


import com.project.feedback.domain.Response;
import com.project.feedback.domain.dto.course.AddStudentRequest;
import com.project.feedback.domain.dto.course.CourseCreateRequest;
import com.project.feedback.domain.dto.course.CourseCreateResponse;
import com.project.feedback.domain.entity.TaskEntity;
import com.project.feedback.domain.entity.User;
import com.project.feedback.service.CourseService;
import com.project.feedback.service.FindService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
@Api(tags = {"기수(Course)"})
public class CourseApiController {

    private final CourseService courseService;
    private final FindService findService;

    @Operation(summary = "기수 등록")
    @PostMapping
    public Response<CourseCreateResponse> write(@RequestBody CourseCreateRequest req, @ApiIgnore Authentication auth) {
        CourseCreateResponse res = courseService.createCourse(req, auth.getName());
        return Response.success(res);
    }

    @Operation(summary = "기수에 학생 등록")
    @PostMapping("/register")
    public String registerStudent(@RequestBody AddStudentRequest studentRequest) {
        courseService.registerStudent(studentRequest);
        return "success";
    }

    // 기수에 속한 학생 목록 output = 학생 이름, 진행 상황 status
    @Operation(summary = "기수에 속한 학생 목록")
    @GetMapping("/{courseId}/students")
    public String getStudents(@PathVariable Long courseId, @ApiIgnore Authentication auth) {
        User loginUser = findService.findUserByUserName(auth.getName());
        List<User> users = findService.findUserByCourseId(courseId, loginUser);
        //학생 각각의 task에 대한 진행상황
        return "success";
    }


    @Operation(summary = "기수에 속한 Task 목록")
    @GetMapping("/{courseId}/tasks")
    public String getTaaks(@PathVariable Long courseId, @ApiIgnore Authentication auth) {
        // userid에 대한 검증 필요?
        User loginUser = findService.findUserByUserName(auth.getName());
        List<TaskEntity> taskEntities = findService.findTaskByCourseId(courseId);
        return "success";
    }

}