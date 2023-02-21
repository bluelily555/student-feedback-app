package com.project.feedback.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.feedback.domain.dto.task.TaskCreateRequest;
import com.project.feedback.fixture.TaskFixture;
import com.project.feedback.service.TaskService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;



import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(TaskApiController.class)
class TaskApiControllerTest {
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @MockBean
//    TaskService taskService;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @Test
//    @DisplayName("태스크 등록 성공")
//    @WithMockUser
//    void taskCreateSuccess() throws Exception {
//
//        TaskCreateRequest taskCreateRequest = TaskFixture.taskCreateRequest("CREATED");
//
//        when(taskService.createTask(any(), any()))
//                .thenReturn(TaskFixture.taskCreateResponse(taskCreateRequest.getTitle()));
//
//        mockMvc.perform(post("/api/v1/tasks")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsBytes(taskCreateRequest))
//                        .with(csrf()))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.result.title").value("Task 제목"))
//                .andExpect(jsonPath("$.result.taskId").value(1));
//
//    }
}