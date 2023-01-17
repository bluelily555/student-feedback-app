package com.project.feedback.service;

import com.project.feedback.domain.dto.task.TaskCreateRequest;
import com.project.feedback.domain.dto.task.TaskCreateResponse;
import com.project.feedback.fixture.CourseFixture;
import com.project.feedback.fixture.TaskFixture;
import com.project.feedback.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class TaskServiceTest {

    TaskRepository taskRepository = mock(TaskRepository.class);
    FindService findService = mock(FindService.class);
    TaskService taskService;

    @BeforeEach
    void setUp(){
        this.taskService = new TaskService(taskRepository, findService);
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
}