package com.project.feedback.controller.ui;

import com.project.feedback.domain.dto.course.CourseDto;
import com.project.feedback.domain.dto.task.TaskCreateRequest;
import com.project.feedback.domain.dto.task.TaskDetailResponse;
import com.project.feedback.domain.dto.task.TaskUpdateRequest;
import com.project.feedback.domain.entity.User;
import com.project.feedback.exception.CustomException;
import com.project.feedback.exception.ErrorCode;
import com.project.feedback.service.CourseService;
import com.project.feedback.service.FindService;
import com.project.feedback.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Slf4j
public class TaskController {
    private final TaskService taskService;
    private final FindService findService;
    private final CourseService courseService;

    @GetMapping("/write")
    public String writePage(Model model) {
        model.addAttribute("taskCreateRequest", new TaskCreateRequest());
        List<CourseDto> courses = courseService.courses();

        model.addAttribute("courseList", courses);

        // todo week, day는 Course의 시작일을 기준으로 주차를 구함
        model.addAttribute("week", 2);
        model.addAttribute("day", 1);
        return "tasks/write";
    }

    @PostMapping("/write")
    public String write(@ModelAttribute TaskCreateRequest req, Authentication auth) {
        taskService.createTask(req, auth.getName());
        return "redirect:/";
    }

    @GetMapping("/{taskId}")
    public String show(@PathVariable Long taskId, Model model, Authentication auth) {
        TaskDetailResponse res = taskService.getOneTask(taskId);
        model.addAttribute("taskDetail", res);

        // 화면에 changeable을 true로 넘겨주면 수정, 삭제 가능
        User taskUser = findService.findUserByUserName(res.getUserName());
        User loginUser = findService.findUserByUserName(auth.getName());
        if(auth != null && findService.checkAuth(taskUser, loginUser)) {
            model.addAttribute("changeable", true);
        } else {
            model.addAttribute("changeable", false);
        }

        return "tasks/show";
    }

    @GetMapping("/{taskId}/delete")
    public String delete(@PathVariable Long taskId, Model model, Authentication auth) {
        try {
            taskService.deleteTask(taskId, auth.getName());
        } catch (CustomException e) {
            if(e.getErrorCode() == ErrorCode.INVALID_PERMISSION) {
                model.addAttribute("message", "작성자만 삭제할 수 있습니다.");
                model.addAttribute("nextUrl", "/tasks/" + taskId);
                model.addAttribute("taskDetail", taskService.getOneTask(taskId));
                return "tasks/show";
            }
        } catch (Exception e) {
            throw e;
        }

        model.addAttribute("message", "글이 삭제 되었습니다.");
        model.addAttribute("nextUrl", "/");
        return "forward:/";
    }

    @PostMapping("/{taskId}/edit")
    public String edit(@PathVariable Long taskId, @ModelAttribute TaskUpdateRequest req, Authentication auth, Model model) {
        model.addAttribute("taskDetail", taskService.getOneTask(taskId));

        try {
            taskService.updateTask(taskId, req, auth.getName());
        } catch (CustomException e) {
            if(e.getErrorCode() == ErrorCode.INVALID_PERMISSION) {
                model.addAttribute("message", "작성자만 수정할 수 있습니다.");
                model.addAttribute("nextUrl", "/tasks/" + taskId);
                return "tasks/show";
            }
        } catch (Exception e) {
            throw e;
        }

        model.addAttribute("message", "글이 수정 되었습니다.");
        model.addAttribute("nextUrl", "/tasks/" + taskId);
        return "tasks/show";
    }
}
