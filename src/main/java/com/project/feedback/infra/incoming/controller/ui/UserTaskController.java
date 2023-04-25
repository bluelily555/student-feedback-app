package com.project.feedback.infra.incoming.controller.ui;

import com.project.feedback.domain.dto.mainInfo.StatusInfo;
import com.project.feedback.infra.outgoing.jpa.UserEntity;
import com.project.feedback.application.FindService;
import com.project.feedback.application.UserTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


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

    @ResponseBody
    @PostMapping("/progressForStudent")
    public Map<String, Long> progressForStudent(@RequestParam String week, @RequestParam String day, @RequestParam String userId, Authentication auth){
        long w = Long.valueOf(week);
        long d = Long.valueOf(day);
        long user_Id = Long.valueOf(userId);
        Map<String, Long> result = userTaskService.progressPercentageByUser(user_Id, w, d);

        return result;
    }

    @ResponseBody
    @PostMapping("/progressForTask")
    public String progressForTask(@RequestParam String taskId, @RequestParam String courseName){
        long task_Id = Long.valueOf(taskId);
        long courseId = findService.findCourseByName(courseName).getId();
        String result = userTaskService.progressPercentageByTask(task_Id, courseId);

        return result;
    }


}
