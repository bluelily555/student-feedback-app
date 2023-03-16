package com.project.feedback.repository;

import com.project.feedback.domain.entity.BoardEntity;
import com.project.feedback.domain.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    List<BoardEntity> findAllByUserId(Long userId);
    Page<BoardEntity> findByTitleContaining(Pageable pageable, String title);
    Page<BoardEntity> findAll(Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "UPDATE BoardEntity entity SET entity.deletedAt = NOW() where entity.taskEntity = :task")
    void deleteAllByTask(@Param("task") TaskEntity taskEntity);
}
