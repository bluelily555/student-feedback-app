package com.project.feedback.api_controller;


import com.project.feedback.domain.Response;
import com.project.feedback.domain.dto.task.TaskCreateRequest;
import com.project.feedback.domain.dto.task.TaskCreateResponse;
import com.project.feedback.domain.dto.task.TaskDeleteResponse;
import com.project.feedback.domain.dto.task.TaskDetailResponse;
import com.project.feedback.domain.dto.task.TaskListResponse;
import com.project.feedback.domain.dto.task.TaskUpdateRequest;
import com.project.feedback.domain.dto.task.TaskUpdateResponse;
import com.project.feedback.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskApiController {

    private final TaskService taskService;

    @Operation(summary = "태스크 작성")
    @PostMapping("/")
    public Response<TaskCreateResponse> write(@RequestBody TaskCreateRequest req, @ApiIgnore Authentication auth) {
        // authentication.getName()을 통해 로그인한 유저의 userName을 꺼내 올 수 있음
        TaskCreateResponse res = taskService.saveTask(req, auth.getName(), req.getCourseName());
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
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)Pageable pageable) {
        TaskListResponse res = taskService.getTaskList(pageable);
        return Response.success(res);
    }
}
