package com.project.feedback.repository;

import com.project.feedback.infra.outgoing.entity.BoardEntity;
import com.project.feedback.infra.outgoing.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    Page<BoardEntity> findAllByUserId(Pageable pageable, Long userId);
    Page<BoardEntity> findByTitleContaining(Pageable pageable, String title);
    Page<BoardEntity> findAll(Pageable pageable);

    Page<BoardEntity> findByTaskEntityId(Pageable pageable, Long taskId);

    List<BoardEntity> findTop10ByCommentsIsNullOrderByIdDesc();
    @Query("SELECT b FROM BoardEntity b LEFT JOIN b.comments WHERE b.id IN :ids")
    List<BoardEntity> findByIdInFetch(List<Long> ids);

    @Transactional
    @Modifying
    @Query(value = "UPDATE BoardEntity entity SET entity.deletedAt = NOW() where entity.taskEntity = :task")
    void deleteAllByTask(@Param("task") TaskEntity taskEntity);

    List<BoardEntity> findByUserId(Long userId);
}
