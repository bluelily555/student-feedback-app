package com.project.feedback.repository;

import com.project.feedback.domain.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
//    List<CommentEntity> findByPost_id(Long id);
    List<CommentEntity> findAllByUserName(String userName);
    List<CommentEntity> findCommentEntityByBoardId(Long boardId);

}
