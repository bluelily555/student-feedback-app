package com.project.feedback.repository;

import com.project.feedback.domain.entity.LikeEntity;
import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.domain.enums.LikeContentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    Optional<LikeEntity> findByContentTypeAndContentIdAndFromUser(LikeContentType type, Long contentId, UserEntity from);
    int countByContentTypeAndContentIdAndStatusIsTrue(LikeContentType type, Long contentId);
}
