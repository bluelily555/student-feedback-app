package com.project.feedback.infra.outgoing.repository;

import com.project.feedback.infra.outgoing.entity.CourseUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseUserRepository extends JpaRepository<CourseUserEntity, Long> {
    Optional<CourseUserEntity> findCourseEntityUserById(Long id);

    Optional<CourseUserEntity> findCourseEntityUserByUserId(Long userId);

    List<CourseUserEntity> findCourseEntityUserByCourseEntityId(Long courseId);

    Long countByCourseEntityId(Long courseId);

}
