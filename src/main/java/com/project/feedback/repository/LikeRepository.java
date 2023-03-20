package com.project.feedback.repository;

import com.project.feedback.domain.entity.LikeEntity;
import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.domain.enums.LikeContentType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    Optional<LikeEntity> findByContentTypeAndContentIdAndFromUser(LikeContentType type, Long contentId, UserEntity from);
    int countByContentTypeAndContentIdAndStatusIsTrue(LikeContentType type, Long contentId);
    @Query("SELECT l.contentId FROM LikeEntity l WHERE l.contentType = 'BOARD' AND l.status = true GROUP BY l.contentId ORDER BY count(l) desc")
    List<Long> findLikesOfBoardRank(Pageable pageable);
}
