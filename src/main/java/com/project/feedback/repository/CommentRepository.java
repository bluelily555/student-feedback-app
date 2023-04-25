package com.project.feedback.repository;


import com.project.feedback.infra.outgoing.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    Page<CommentEntity> findByBoardEntityId(Long boardId, Pageable pageable);
    // List<CommentEntity> findByUserIdOrderByCreatedDateDesc(Long userId);
    int countByBoardEntityId(Long boardId);

    int countByUserId(Long userId);

    List<CommentEntity> findByUserId(Long userId);

}
