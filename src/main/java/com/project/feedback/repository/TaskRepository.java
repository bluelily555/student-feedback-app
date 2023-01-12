package com.project.feedback.repository;

import com.project.feedback.domain.entity.TaskEntity;
import com.project.feedback.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
   // Optional<TaskEntity> findByUserName(String userName);

    Page<TaskEntity> findAll(Pageable pageable);
}
