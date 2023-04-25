package com.project.feedback.infra.outgoing.adapter;

import com.project.feedback.infra.outgoing.entity.UserTaskEntity;
import com.project.feedback.infra.outgoing.repository.UserTaskRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserTaskAdapter {
    private final UserTaskRepository userTaskRepository;

    public UserTaskAdapter(UserTaskRepository userTaskRepository) {
        this.userTaskRepository = userTaskRepository;
    }

    public List<UserTaskEntity> findByUserIdAndTaskEntityIdIn(List<Long> userId, List<Long> taskId){
        return userTaskRepository.findByUserIdAndTaskEntityIdIn(userId, taskId);
    };

}
