package com.project.feedback.service;

import com.project.feedback.domain.dto.course.AddStudentRequest;
import com.project.feedback.domain.dto.course.CourseCreateRequest;
import com.project.feedback.domain.dto.course.CourseCreateResponse;
import com.project.feedback.domain.dto.course.CourseDto;
import com.project.feedback.domain.entity.CourseEntity;
import com.project.feedback.domain.entity.CourseUserEntity;
import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.repository.CourseRepository;
import com.project.feedback.repository.CourseUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseUserRepository courseUserRepository;
    private final FindService findService;

    public int getCourseLength(){
        return courseRepository.findAll().size();
    }
    public List<CourseDto> courses() {
        return courseRepository.findAll().stream()
            .map(courseEntity -> CourseDto.fromEntity(courseEntity)).collect(Collectors.toList());
    }

    public CourseCreateResponse createCourse(CourseCreateRequest req, String userName) {
        // UserName이 존재하는지 체크
        UserEntity writeUser = findService.findUserByUserName(userName);
        CourseEntity savedCourse = courseRepository.save(req.toEntity(writeUser));
        return CourseCreateResponse.of(savedCourse.getId(), savedCourse.getName());
    }


    public void registerStudent(AddStudentRequest req) {
        CourseEntity course = findService.findCourseByName(req.getCourseName());

        //기수 등록해야하는 학생 list
        List<UserEntity> users = req.getUserList();

        for(UserEntity user : users){
            if(!courseUserRepository.findCourseEntityUserByUserId(user.getId()).isPresent()){
                CourseUserEntity courseUserEntity = new CourseUserEntity();
                courseUserEntity.setUser(user);
                courseUserEntity.setCourseEntity(course);
                courseUserRepository.save(courseUserEntity);
            }
        }
        courseRepository.save(course);
    }

    public void setDefaultCourse() {
        CourseCreateResponse courseCreateResponse;

        if (courseRepository.count() == 0) {
            CourseCreateRequest courseCreateRequest = CourseCreateRequest.builder()
                    .name("기본 기수1")
                    .status("CREATED")
                    .startDate(java.sql.Timestamp.valueOf(LocalDateTime.now()))
                    .endDate(java.sql.Timestamp.valueOf(LocalDateTime.now().plusMinutes(1)))
                    .description("")
                    .build();

            log.info("기본기수 등록 {}", courseCreateRequest);
            courseCreateResponse = createCourse(courseCreateRequest, "student");
        }

    }

}
