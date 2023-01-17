package com.project.feedback.service;

import com.project.feedback.domain.dto.course.AddStudentRequest;
import com.project.feedback.domain.dto.course.CourseCreateRequest;
import com.project.feedback.domain.dto.course.CourseCreateResponse;
import com.project.feedback.domain.dto.course.CourseDto;
import com.project.feedback.domain.entity.CourseEntity;
import com.project.feedback.domain.entity.User;
import com.project.feedback.exception.CustomException;
import com.project.feedback.exception.ErrorCode;
import com.project.feedback.repository.CourseRepository;
import com.project.feedback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final FindService findService;

    public List<CourseDto> courses() {
        return courseRepository.findAll().stream()
            .map(courseEntity -> CourseDto.fromEntity(courseEntity)).collect(Collectors.toList());
    }

    public CourseCreateResponse createCourse(CourseCreateRequest req, String userName) {
        // UserName이 존재하는지 체크
        User writeUser = findService.findUserByUserName(userName);
        CourseEntity savedCourse = courseRepository.save(req.toEntity(writeUser));
        return CourseCreateResponse.of(savedCourse.getId(), savedCourse.getName());
    }


    // to do 작성 중
    public void registerStudent(Long courseId, AddStudentRequest req) {
        CourseEntity course = courseRepository.findById(courseId)
            .orElseThrow(() -> new CustomException(ErrorCode.COURSE_NOT_FOUND));

        User user = userRepository.findById(req.getUserId())
            .orElseThrow(() -> new CustomException(ErrorCode.USERNAME_NOT_FOUND));

        // user 추가
        List<User> users = course.getUsers();
        users.add(user);
        course.setUsers(users);
        courseRepository.save(course);
    }


}
