package com.project.feedback.controller.api;


import com.project.feedback.domain.Response;
import com.project.feedback.domain.dto.task.*;
import com.project.feedback.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskApiController {

    private final TaskService taskService;

    @PostMapping
    public Response<TaskCreateResponse> write(@RequestBody TaskCreateRequest req, Authentication auth) {
        // authentication.getName()을 통해 로그인한 유저의 userName을 꺼내 올 수 있음
        TaskCreateResponse res = taskService.createTask(req, auth.getName());
        return Response.success(res);
    }

    @GetMapping("/{taskId}")
    public Response<TaskDetailResponse> show(@PathVariable Long taskId) {
        TaskDetailResponse res = taskService.getOneTask(taskId);
        return Response.success(res);
    }

    @PutMapping("/{taskId}")
    public Response<TaskUpdateResponse> update(@PathVariable Long taskId, @RequestBody TaskUpdateRequest req,
                                               Authentication auth) {
        TaskUpdateResponse res = taskService.updateTask(taskId, req, auth.getName());
        return Response.success(res);
    }

    @DeleteMapping("/{taskId}")
    public Response<TaskDeleteResponse> delete(@PathVariable Long taskId, Authentication auth) {
        TaskDeleteResponse res = taskService.deleteTask(taskId, auth.getName());
        return Response.success(res);
    }

    @GetMapping("")
    public Response<TaskListResponse> list(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        TaskListResponse res = taskService.getTaskList(pageable);
        return Response.success(res);
    }


    @GetMapping("weeks/{week}/days/{day}")
    public Response<List<TaskListDto>> listByWeekAndDay(@PathVariable Long week, @PathVariable Long day) {
        List<TaskListDto> res = taskService.getTaskListByWeekAndDay(week, day);
        return Response.success(res);
    }
}