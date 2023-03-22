package com.project.feedback.repository;

import com.project.feedback.domain.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
    List<ImageEntity> findByNameIn(List<String> names);
}