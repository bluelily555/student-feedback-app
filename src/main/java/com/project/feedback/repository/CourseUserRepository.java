package com.project.feedback.repository;

import com.project.feedback.domain.entity.CourseEntityUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseUserRepository extends JpaRepository<CourseEntityUser, Long> {
    Optional<CourseEntityUser> findCourseEntityUserById(Long id);

    Optional<CourseEntityUser> findCourseEntityUserByUserId(Long userId);

    List<CourseEntityUser> findCourseEntityUserByCourseEntityId(Long courseId);

}
