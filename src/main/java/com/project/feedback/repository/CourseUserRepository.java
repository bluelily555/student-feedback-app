package com.project.feedback.repository;

import com.project.feedback.domain.entity.CourseEntityUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseUserRepository extends JpaRepository<CourseEntityUser, Long> {
    Optional<CourseEntityUser> findCourseEntityUserById(Long courseName);
    Optional<CourseEntityUser> findCourseEntityUserByUserId(Long userId);

}
