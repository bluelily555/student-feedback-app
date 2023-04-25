package com.project.feedback.application.entity;


import com.project.feedback.domain.TaskStatus;
import com.project.feedback.domain.dto.mainInfo.StatusInfo;
import com.project.feedback.domain.dto.mainInfo.StudentInfo;
import com.project.feedback.infra.outgoing.jpa.TaskEntity;
import com.project.feedback.infra.outgoing.jpa.UserEntity;
import com.project.feedback.infra.outgoing.jpa.UserTaskEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Setter
public class CourseStudent {
    private Course course;
    private List<User> users;
    private List<Task> tasks;
    private List<UserTask> userTasks;

    public CourseStudent(List<Task> tasks) {
        this.tasks = tasks;
    }

    public CourseStudent(List<TaskEntity> taskEntities, List<UserEntity> userEntities) {

        List<Task> tasks = taskEntities.stream()
                .map(taskEntity -> Task.fromEntity(taskEntity))
                .collect(Collectors.toList());


        List<User> users = userEntities.stream()
                .map(userEntity -> User.fromEntity(userEntity))
                .collect(Collectors.toList());


        this.tasks = tasks;
        this.users = users;
    }

    public List<Long> getTaskIds() {
        List<Long> taskIds = new ArrayList<>();
        for(Task task : tasks){
            taskIds.add(task.getId());
        }
        return taskIds;
    }

    public List<Long> getUserIds() {
        List<Long> userIds = new ArrayList<>();
        for(User user : users){
            userIds.add(user.getId());
        }
        return userIds;
    }

    public List<StatusInfo> statusInfo() {
        List<StatusInfo> statusInfos = new ArrayList<>();
        for(User user : users){

            List<Long> userIds = new ArrayList(
                    Collections.nCopies(this.tasks.size(), user.getId())
            );


        }
        return statusInfos;
    }



}
