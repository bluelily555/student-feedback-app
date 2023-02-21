package com.project.feedback.repository;

import com.project.feedback.domain.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    List<BoardEntity> findAllByUserId(Long userId);
}
