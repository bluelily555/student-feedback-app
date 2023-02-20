package com.project.feedback.repository;


import com.project.feedback.domain.entity.CommentsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentsRepository extends JpaRepository<CommentsEntity, Long> {
    Page<CommentsEntity> findByCodeEntityId(Long codeId, Pageable pageable);
   // List<CommentsEntity> findByUserIdOrderByCreatedDateDesc(Long userId);

}
