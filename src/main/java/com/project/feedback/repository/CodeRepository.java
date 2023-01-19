package com.project.feedback.repository;

import com.project.feedback.domain.entity.CodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeRepository extends JpaRepository<CodeEntity, Long> {
}
