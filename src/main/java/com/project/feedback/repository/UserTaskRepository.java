package com.project.feedback.repository;

import com.project.feedback.domain.entity.TaskEntity;
import com.project.feedback.domain.entity.UserTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserTaskRepository extends JpaRepository<UserTaskEntity, Long> {
   List<UserTaskEntity> findByUserId(Long userId);

   Long countByTaskEntityId(Long taskId);

    UserTaskEntity findByUserIdAndTaskEntityId(Long userId, Long taskId);

    @Query(value = "select count(*) from UserTaskEntity as userTask where  userTask.taskEntity.id = :taskId and userTask.status = 'DONE'")
    Long countByTaskEntityIdAndStatus(@Param("taskId") Long taskId);



}
