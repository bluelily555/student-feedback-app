package com.project.feedback.service;

import com.project.feedback.domain.dto.course.CourseCreateRequest;
import com.project.feedback.domain.dto.course.CourseCreateResponse;
import com.project.feedback.domain.dto.course.CourseDto;
import com.project.feedback.domain.entity.CourseEntity;
import com.project.feedback.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public List<CourseDto> courses() {
        return courseRepository.findAll().stream()
            .map(courseEntity -> CourseDto.fromEntity(courseEntity)).collect(Collectors.toList());
    }

    public CourseCreateResponse createCourse(CourseCreateRequest req) {
        CourseEntity savedCourse = courseRepository.save(req.toEntity());
        return CourseCreateResponse.of(savedCourse.getId(), savedCourse.getName());
    }

    //to do
    public void getOneCourse(Long courseId){
    }



}
