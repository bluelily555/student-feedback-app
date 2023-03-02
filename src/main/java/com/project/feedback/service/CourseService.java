package com.project.feedback.service;

import com.project.feedback.domain.dto.course.AddStudentRequest;
import com.project.feedback.domain.dto.course.CourseCreateRequest;
import com.project.feedback.domain.dto.course.CourseCreateResponse;
import com.project.feedback.domain.dto.course.CourseDto;
import com.project.feedback.domain.entity.CourseEntity;
import com.project.feedback.domain.entity.CourseUserEntity;
import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.exception.CustomException;
import com.project.feedback.exception.ErrorCode;
import com.project.feedback.repository.CourseRepository;
import com.project.feedback.repository.CourseUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public CourseEntity findByCourseName(String courseName){
        CourseEntity courseEntity = courseRepository.findByName(courseName)
            .orElseThrow(()-> new CustomException(ErrorCode.COURSE_NOT_FOUND));
        return courseEntity;

    }

    public CourseEntity findByCourseId(Long courseId){
        CourseEntity courseEntity = courseRepository.findById(courseId)
            .orElseThrow(()-> new CustomException(ErrorCode.COURSE_NOT_FOUND));
        return courseEntity;

    }


    public void registerStudent(AddStudentRequest req) {
        CourseEntity course = findService.findCourseByName(req.getCourseName());

        //기수 등록해야하는 학생 list
        List<UserEntity> users = req.getUserList();
        CourseUserEntity courseUserEntity;
        for(UserEntity user : users){
            if(!courseUserRepository.findCourseEntityUserByUserId(user.getId()).isPresent()){
                courseUserEntity = new CourseUserEntity();
                courseUserEntity.setUser(user);
                courseUserEntity.setCourseEntity(course);
            }
            else{ // 존재 한다면
                courseUserEntity = courseUserRepository.findCourseEntityUserByUserId(user.getId()).orElseThrow(() -> new CustomException(ErrorCode.USER_COURSE_NOT_FOUND));
                courseUserEntity.setCourseEntity(course);
            }
            courseUserRepository.save(courseUserEntity);
        }
    }

    public void setDefaultCourse() {
        CourseCreateResponse courseCreateResponse;

        if (courseRepository.count() == 0) {
            CourseCreateRequest courseCreateRequest = CourseCreateRequest.builder()
                    .name("기본 기수1")
                    .status("CREATED")
                    .startDate(LocalDate.now())
                    .endDate(LocalDate.now().plusDays(1))
                    .description("")
                    .build();

            log.info("기본기수 등록 {}", courseCreateRequest);
            courseCreateResponse = createCourse(courseCreateRequest, "student");
        }

    }

}
