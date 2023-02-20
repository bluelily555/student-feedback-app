package com.project.feedback.repository;

import com.project.feedback.domain.entity.CodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CodeRepository extends JpaRepository<CodeEntity, Long> {
    List<CodeEntity> findAllByUserName(String userName);
    List<CodeEntity> findAllByTaskId(Long taskId);
}
