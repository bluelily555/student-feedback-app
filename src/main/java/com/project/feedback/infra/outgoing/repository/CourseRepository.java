package com.project.feedback.infra.outgoing.repository;

import com.project.feedback.infra.outgoing.entity.CourseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<CourseEntity, Long> {
    Optional<CourseEntity> findByName(String courseName);

    Page<CourseEntity> findAll(Pageable pageable);

}
