package com.project.feedback.infra.outgoing.repository;

import com.project.feedback.infra.outgoing.jpa.UserTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;


public interface UserTaskRepository extends JpaRepository<UserTaskEntity, Long>{
   List<UserTaskEntity> findByUserId(Long userId);

   Long countByTaskEntityId(Long taskId);
   List<UserTaskEntity> findAllByUserId(Long userId);

    UserTaskEntity findByUserIdAndTaskEntityId(Long userId, Long taskId);


    @Query(value = "select userTask from UserTaskEntity as userTask where  userTask.user.id in (:userId) and userTask.taskEntity.id in (:taskId) ")
    List<UserTaskEntity> findByUserIdAndTaskEntityIdIn(@Param("userId") List<Long> userId, @Param("taskId")List<Long> taskId );

    @Query(value = "select userTask from UserTaskEntity as userTask where  userTask.user.id in (:userId) ")
    List<UserTaskEntity> findByUserIdIn(@Param("userId") List<Long> userId);

    @Query(value = "select count(*) from UserTaskEntity as userTask where  userTask.taskEntity.id = :taskId and userTask.status = 'DONE'")
    Long countByTaskEntityIdAndStatus(@Param("taskId") Long taskId);



}
