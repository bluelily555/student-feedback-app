package com.project.feedback.infra.outgoing.adapter;

import com.project.feedback.infra.outgoing.jpa.UserTaskEntity;
import com.project.feedback.infra.outgoing.repository.UserTaskRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserTaskAdapter {
    private final UserTaskRepository userTaskRepository;

    public UserTaskAdapter(UserTaskRepository userTaskRepository) {
        this.userTaskRepository = userTaskRepository;
    }

    public List<UserTaskEntity> findByUserIdAndTaskEntityIdIn(List<Long> userIds, List<Long> taskIds){
        return userTaskRepository.findByUserIdAndTaskEntityIdIn(userIds, taskIds);
    };

    public List<UserTaskEntity> findByUserIdIn(List<Long> userId){
        return userTaskRepository.findByUserIdIn(userId);
    }

}
