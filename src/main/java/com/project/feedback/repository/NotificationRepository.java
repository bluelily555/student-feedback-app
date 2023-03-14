package com.project.feedback.repository;

import com.project.feedback.domain.entity.NotificationEntity;
import com.project.feedback.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    @Query("select n from NotificationEntity n  join fetch n.targetUser join fetch n.fromUser where n.targetUser = :targetUser")
    List<NotificationEntity> findByTargetUserFetch(UserEntity targetUser);
}