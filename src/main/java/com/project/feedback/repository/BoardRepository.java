package com.project.feedback.repository;

import com.project.feedback.domain.entity.BoardEntity;
import com.project.feedback.domain.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    List<BoardEntity> findAllByUserName (String userName);
}
