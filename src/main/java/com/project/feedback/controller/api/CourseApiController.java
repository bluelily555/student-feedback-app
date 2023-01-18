package com.project.feedback.controller.api;


import com.project.feedback.domain.Response;
import com.project.feedback.domain.dto.course.AddStudentRequest;
import com.project.feedback.domain.dto.course.CourseCreateRequest;
import com.project.feedback.domain.dto.course.CourseCreateResponse;
import com.project.feedback.service.CourseService;
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

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
@Api(tags = {"기수(Course)"})
public class CourseApiController {

    private final CourseService courseService;

    @Operation(summary = "기수 등록")
    @PostMapping
    public Response<CourseCreateResponse> write(@RequestBody CourseCreateRequest req, @ApiIgnore Authentication auth) {
        CourseCreateResponse res = courseService.createCourse(req, auth.getName());
        return Response.success(res);
    }

    @Operation(summary = "기수에 학생 등록")
    @PostMapping("/{courseId}/students")
    public String registerStudent(@PathVariable Long courseId, @RequestBody AddStudentRequest studentRequest, @ApiIgnore Authentication auth) {
        courseService.registerStudent(courseId, studentRequest);
        return "success";
    }

}