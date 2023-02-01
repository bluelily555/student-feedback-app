package com.project.feedback.repository;

import com.project.feedback.domain.entity.UserTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTaskRepository extends JpaRepository<UserTaskEntity, Long> {
    List<UserTaskEntity> findByUserId(Long userId);

   UserTaskEntity findByUserIdAndTaskEntityId(Long userId, Long taskId);
}
