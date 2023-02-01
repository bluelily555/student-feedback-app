package com.project.feedback.repository;

import com.project.feedback.domain.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
   // Optional<TaskEntity> findByUserName(String userName);

    Page<TaskEntity> findAll(Pageable pageable);

    List<TaskEntity> findByWeekAndDayOfWeek(Long week, Long day);

    @Query(value = "select task from TaskEntity task where task.week = :week and task.dayOfWeek = :day and task.courseEntity.id = :courseId")
    List<TaskEntity> findByCourseIdAndWeekAndDay(@Param("courseId") Long courseId, @Param("week") Long week, @Param("day") Long day);
}
