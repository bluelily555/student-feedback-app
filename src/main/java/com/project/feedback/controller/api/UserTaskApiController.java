package com.project.feedback.controller.api;

import com.project.feedback.domain.dto.mainInfo.StatusInfo;
import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.service.FindService;
import com.project.feedback.service.UserTaskService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1/userTasks")
@RequiredArgsConstructor
@Api(tags = {"유저 태스크(Task)"})
public class UserTaskApiController {
    private final FindService findService;
    private final UserTaskService userTaskService;

    @Operation(summary = "유저태스크 등록")
    @PostMapping
    public String write(@RequestBody StatusInfo req, @ApiIgnore Authentication auth) {
        // authentication.getName()을 통해 로그인한 유저의 userName을 꺼내 올 수 있음
        UserEntity loginUser = findService.findUserByUserName(auth.getName());
        userTaskService.createUserTask(req, loginUser);
        return "success";
    }

    @Operation(summary = "유저태스크 상태 수정")
    @PostMapping("users/{userId}/tasks/{taskId}")
    public String update(@PathVariable Long taskId, @PathVariable Long userId, @RequestBody String status,
                                               @ApiIgnore Authentication auth) {
        userTaskService.updateUserTask(taskId, userId, status);
        return "success";
    }
}
