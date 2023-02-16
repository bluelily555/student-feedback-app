package com.project.feedback.controller.ui;

import com.project.feedback.domain.dto.course.CourseDto;
import com.project.feedback.domain.dto.course.CourseInfo;
import com.project.feedback.domain.dto.task.TaskCreateRequest;
import com.project.feedback.domain.dto.task.TaskDetailResponse;
import com.project.feedback.domain.dto.task.TaskListResponse;
import com.project.feedback.domain.dto.task.TaskUpdateRequest;
import com.project.feedback.domain.entity.CourseEntity;
import com.project.feedback.exception.CustomException;
import com.project.feedback.exception.ErrorCode;
import com.project.feedback.service.CourseService;
import com.project.feedback.service.FindService;
import com.project.feedback.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/userTasks")
@RequiredArgsConstructor
@Slf4j
public class UserTaskController {
    private final FindService findService;

    @ResponseBody
    @PostMapping("/sendUserTask")
    public Map<String, String> addUserTask(@RequestParam String courseName, Model model){
        CourseEntity courseEntity = findService.findCourseByName(courseName);

        int day = CourseInfo.fromEntity(courseEntity).getDayOfWeek();
        long week = CourseInfo.fromEntity(courseEntity).getWeek(courseEntity.getStartDate());

        Map<String, String> map = new HashMap<>();
        map.put("week", String.valueOf(week));
        map.put("day",String.valueOf(day));
        return map;
    }


}
