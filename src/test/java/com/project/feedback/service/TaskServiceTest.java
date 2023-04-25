package com.project.feedback.service;

import com.project.feedback.application.FindService;
import com.project.feedback.application.TaskService;
import com.project.feedback.domain.Role;
import com.project.feedback.domain.dto.task.TaskCreateRequest;
import com.project.feedback.domain.dto.task.TaskCreateResponse;
import com.project.feedback.domain.dto.task.TaskDeleteResponse;
import com.project.feedback.infra.outgoing.entity.BoardEntity;
import com.project.feedback.infra.outgoing.entity.TaskEntity;
import com.project.feedback.infra.outgoing.entity.UserEntity;
import com.project.feedback.exception.CustomException;
import com.project.feedback.exception.ErrorCode;
import com.project.feedback.fixture.CourseFixture;
import com.project.feedback.fixture.TaskFixture;
import com.project.feedback.repository.BoardRepository;
import com.project.feedback.infra.outgoing.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class TaskServiceTest {

    TaskRepository taskRepository = mock(TaskRepository.class);
    BoardRepository boardRepository = mock(BoardRepository.class);
    FindService findService = mock(FindService.class);
    TaskService taskService;

    @BeforeEach
    void setUp(){
        this.taskService = new TaskService(taskRepository, boardRepository, findService);
    }

    @Test
    @DisplayName("Task등록 성공")
    void task_create_success() {
        TaskCreateRequest taskCreateRequest = TaskFixture.taskCreateRequest("CREATED");
        String userName = "kyeongrok";

        given(findService.findCourseByName(taskCreateRequest.getCourseName()))
                .willReturn(CourseFixture.courseEntity());

        given(taskRepository.save(any()))
                .willReturn(TaskFixture.taskEntity(1l));

        TaskCreateResponse taskCreateResponse = taskService.createTask(taskCreateRequest, userName);
        assertEquals(1l, taskCreateResponse.getTaskId());
    }

    @Test
    @DisplayName("Task 삭제 성공")
    void task_delete_success() {
        String userName = "kyeongrok";
        UserEntity user = UserEntity.builder()
                        .id(1l)
                        .role(Role.ROLE_ADMIN)
                        .userName(userName)
                        .build();
        TaskEntity findTask = TaskFixture.taskEntity(1l);
        findTask.setUser(user);
        BoardEntity boardEntity = BoardEntity.builder()
            .id(1l)
            .taskEntity(findTask)
            .build();

        given(findService.findTaskById(1l)).willReturn(findTask);
        given(findService.findUserByUserName(userName)).willReturn(user);
        given(findService.checkAuth(user, user)).willReturn(true);


        TaskDeleteResponse taskDeleteResponse = taskService.deleteTask(1l, userName);
        assertEquals(1l, taskDeleteResponse.getTaskId());
    }

    @Test
    @DisplayName("Task 삭제 실패_로그인한 USER가 아님")
    void task_delete_fail() {
        String userName = "kyeongrok";
        UserEntity user = UserEntity.builder()
            .id(1l)
            .role(Role.ROLE_ADMIN)
            .userName(userName)
            .build();
        TaskEntity findTask = TaskFixture.taskEntity(1l);
        findTask.setUser(user);

        given(findService.findTaskById(1l)).willReturn(findTask);
        given(findService.findUserByUserName(userName)).willReturn(user);
        given(findService.checkAuth(user, user)).willReturn(false);

        CustomException e = assertThrows(CustomException.class, () -> taskService.deleteTask(1L, userName));
        assertEquals(ErrorCode.INVALID_PERMISSION, e.getErrorCode()); }
}