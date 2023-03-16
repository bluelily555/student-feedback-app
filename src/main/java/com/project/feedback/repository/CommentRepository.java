package com.project.feedback.repository;


import com.project.feedback.domain.entity.CommentEntity;
import com.project.feedback.domain.enums.LikeContentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    Page<CommentEntity> findByBoardEntityId(Long boardId, Pageable pageable);
   // List<CommentEntity> findByUserIdOrderByCreatedDateDesc(Long userId);
   int countByBoardEntityId(Long boardId);

}
