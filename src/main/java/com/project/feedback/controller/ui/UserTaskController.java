package com.project.feedback.controller.ui;

import com.project.feedback.domain.dto.mainInfo.StatusInfo;
import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.service.FindService;
import com.project.feedback.service.UserTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/userTasks")
@RequiredArgsConstructor
@Slf4j
public class UserTaskController {
    private final UserTaskService userTaskService;
    private final FindService findService;

    @ResponseBody
    @PostMapping("/sendUserTask")
    public String addUserTask(@RequestParam String taskId, @RequestParam String userId, @RequestParam String status, @RequestParam String taskName, Authentication auth){
        String result = "NOT_USER";
        UserEntity loginUser = findService.findUserByUserName(auth.getName());
        long user_Id = Long.valueOf(userId);
        long task_Id = Long.valueOf(taskId);
        if(loginUser.getId() == user_Id){
            if(findService.checkUserTask(user_Id, task_Id) == true){
                userTaskService.updateUserTask(StatusInfo.of(task_Id, taskName, status), loginUser);
            } else{
                userTaskService.createUserTask(StatusInfo.of(task_Id, taskName, status), loginUser);
            }
            result="DONE";
        }
        return result;
    }


}
