package com.project.feedback.service;

import com.project.feedback.domain.TaskStatus;
import com.project.feedback.domain.dto.mainInfo.StatusInfo;
import com.project.feedback.domain.entity.TaskEntity;
import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.domain.entity.UserTaskEntity;
import com.project.feedback.exception.CustomException;
import com.project.feedback.exception.ErrorCode;
import com.project.feedback.repository.TaskRepository;
import com.project.feedback.repository.UserTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserTaskService {

    private final UserTaskRepository userTaskRepository;
    private final TaskRepository taskRepository;

    @Transactional
    public void createUserTask(StatusInfo req, UserEntity user){
        TaskEntity taskEntity = taskRepository.findById(req.getTaskId())
            .orElseThrow(() -> new CustomException(ErrorCode.TASK_NOT_FOUND));

        UserTaskEntity userTaskEntity = new UserTaskEntity();
        userTaskEntity.setTaskEntity(taskEntity);
        userTaskEntity.setUser(user);
        userTaskEntity.setStatus(TaskStatus.valueOf(req.getTaskStatus()));

        userTaskRepository.save(userTaskEntity);
    }

    @Transactional
    public void updateUserTask(StatusInfo req, UserEntity user){
        TaskEntity taskEntity = taskRepository.findById(req.getTaskId())
            .orElseThrow(() -> new CustomException(ErrorCode.TASK_NOT_FOUND));

        UserTaskEntity userTaskEntity = userTaskRepository.findByUserIdAndTaskEntityId(user.getId(), req.getTaskId());
        userTaskEntity.setTaskEntity(taskEntity);
        userTaskEntity.setUser(user);
        userTaskEntity.setStatus(TaskStatus.valueOf(req.getTaskStatus()));

        userTaskRepository.save(userTaskEntity);
    }

    @Transactional
    public void updateUserTask(Long taskId, Long userId, String status){
        UserTaskEntity userTaskEntity = userTaskRepository.findByUserIdAndTaskEntityId(userId, taskId);

        userTaskEntity.setStatus(TaskStatus.valueOf(status));
        userTaskRepository.save(userTaskEntity);
    }

}
