package com.project.feedback.service;

import com.project.feedback.domain.dto.course.AddStudentRequest;
import com.project.feedback.domain.dto.course.CourseCreateRequest;
import com.project.feedback.domain.dto.course.CourseCreateResponse;
import com.project.feedback.domain.dto.course.CourseDto;
import com.project.feedback.domain.entity.CourseEntity;
import com.project.feedback.domain.entity.CourseEntityUser;
import com.project.feedback.domain.entity.User;
import com.project.feedback.repository.CourseRepository;
import com.project.feedback.repository.CourseUserRepository;
import com.project.feedback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseUserRepository courseUserRepository;
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


    public void registerStudent(AddStudentRequest req) {
        CourseEntity course = findService.findCourseByName(req.getCourseName());

        //기수 등록해야하는 학생 list
        List<User> users = req.getUserList();

        for(User user : users){
            if(!courseUserRepository.findCourseEntityUserByUserId(user.getId()).isPresent()){
                CourseEntityUser courseEntityUser = new CourseEntityUser();
                courseEntityUser.setUser(user);
                courseEntityUser.setCourseEntity(course);
                courseUserRepository.save(courseEntityUser);
            }
        }
        courseRepository.save(course);
    }


}
