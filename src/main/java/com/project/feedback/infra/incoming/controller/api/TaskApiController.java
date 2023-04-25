package com.project.feedback.infra.incoming.controller.api;


import com.project.feedback.domain.Response;
import com.project.feedback.domain.dto.task.*;
import com.project.feedback.application.TaskService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Api(tags = {"태스크(Task)"})
public class TaskApiController {

    private final TaskService taskService;

    @Operation(summary = "태스크 작성")
    @PostMapping
    public Response<TaskCreateResponse> write(@RequestBody TaskCreateRequest req, @ApiIgnore Authentication auth) {
        // authentication.getName()을 통해 로그인한 유저의 userName을 꺼내 올 수 있음
        TaskCreateResponse res = taskService.createTask(req, auth.getName());
        return Response.success(res);
    }

    @Operation(summary = "태스크 조회(1개)")
    @GetMapping("/{taskId}")
    public Response<TaskDetailResponse> show(@PathVariable Long taskId) {
        TaskDetailResponse res = taskService.getOneTask(taskId);
        return Response.success(res);
    }

    @Operation(summary = "태스크 수정")
    @PutMapping("/{taskId}")
    public Response<TaskUpdateResponse> update(@PathVariable Long taskId, @RequestBody TaskUpdateRequest req,
                                               @ApiIgnore Authentication auth) {
        TaskUpdateResponse res = taskService.updateTask(taskId, req, auth.getName());
        return Response.success(res);
    }

    @Operation(summary = "태스크 삭제")
    @DeleteMapping("/{taskId}")
    public Response<TaskDeleteResponse> delete(@PathVariable Long taskId, @ApiIgnore Authentication auth) {
        TaskDeleteResponse res = taskService.deleteTask(taskId, auth.getName());
        return Response.success(res);
    }

    @Operation(summary = "태스크 리스트 조회(20개)")
    @GetMapping("")
    public Response<TaskListResponse> list(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        TaskListResponse res = taskService.getTaskList(pageable);
        return Response.success(res);
    }


    @Operation(summary = "week, day로 태스크 리스트 조회")
    @GetMapping("weeks/{week}/days/{day}")
    public Response<List<TaskListDto>> listByWeekAndDay(@PathVariable Long week, @PathVariable Long day) {
        List<TaskListDto> res = taskService.getTaskListByWeekAndDay(week, day);
        return Response.success(res);
    }
}