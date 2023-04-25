package com.project.feedback.repository;

import com.project.feedback.infra.outgoing.jpa.NotificationEntity;
import com.project.feedback.infra.outgoing.jpa.UserEntity;
import com.project.feedback.domain.enums.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    @Query("select n from NotificationEntity n join fetch n.fromUser where n.targetUser = :targetUser order by n.id desc")
    List<NotificationEntity> findByTargetUserFetch(UserEntity targetUser);

    void deleteByTypeInAndSourceId(Collection<NotificationType> types, Long sourceId);
}