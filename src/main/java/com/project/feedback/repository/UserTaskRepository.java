package com.project.feedback.repository;

import com.project.feedback.domain.entity.UserTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserTaskRepository extends JpaRepository<UserTask, Long> {
    List<UserTask> findByUserId(Long userId);

   UserTask findByUserIdAndTaskEntityId(Long userId, Long taskId);
}
