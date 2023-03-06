package com.project.feedback.controller.ui;

import com.project.feedback.domain.dto.board.BoardListDto;
import com.project.feedback.domain.dto.course.CourseDto;
import com.project.feedback.domain.dto.course.CourseInfo;
import com.project.feedback.domain.dto.task.TaskCreateRequest;
import com.project.feedback.domain.dto.task.TaskDetailResponse;
import com.project.feedback.domain.dto.task.TaskFilterInfo;
import com.project.feedback.domain.dto.task.TaskListResponse;
import com.project.feedback.domain.dto.task.TaskUpdateRequest;
import com.project.feedback.domain.entity.CourseEntity;
import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.exception.CustomException;
import com.project.feedback.exception.ErrorCode;
import com.project.feedback.service.CourseService;
import com.project.feedback.service.FindService;
import com.project.feedback.service.LikeService;
import com.project.feedback.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Slf4j
public class TaskController {
    private final TaskService taskService;
    private final FindService findService;
    private final CourseService courseService;
    private final LikeService likeService;

    @GetMapping
    public String list(Model model, @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        TaskListResponse res = taskService.getTaskList(pageable);
        model.addAttribute("taskList", res.getContent());
        model.addAttribute("nowPage", res.getPageable().getPageNumber() + 1);
        model.addAttribute("lastPage", res.getTotalPages());

        TaskFilterInfo taskFilterInfo = new TaskFilterInfo();
        model.addAttribute("taskFilterInfo", taskFilterInfo);

        List<CourseDto> courses = courseService.courses();
        CourseEntity courseEntity = findService.findCourseByName(courses.get(0).getName());
        long week = CourseInfo.fromEntity(courseEntity).getWeek(courseEntity.getStartDate());

        model.addAttribute("week", week);
        model.addAttribute("courseName", courseEntity.getName());
        model.addAttribute("courseList", courses);

        return "tasks/show";
    }

    @GetMapping("/courses/{courseId}")
    public String listByCourseId(Model model, @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long courseId) {
        TaskListResponse res = taskService.getTaskListByCourseId(pageable, courseId);
        model.addAttribute("taskList", res.getContent());
        model.addAttribute("nowPage", res.getPageable().getPageNumber() + 1);
        model.addAttribute("lastPage", res.getTotalPages());

        List<CourseDto> courses = courseService.courses();
        model.addAttribute("courseList", courses);
        CourseEntity courseEntity = courseService.findByCourseId(courseId);

        model.addAttribute("courseName", courseEntity.getName());

        TaskFilterInfo taskFilterInfo = TaskFilterInfo.builder().courseName(courseEntity.getName()).build();
        model.addAttribute("taskFilterInfo", taskFilterInfo);

        return "tasks/show";
    }


    //기수, 주차 필터링해서 task 목록 출력
    @PostMapping("/filter")
    public String weekAndDaySubmit(@ModelAttribute TaskFilterInfo taskFilterInfo, RedirectAttributes redirectAttribute, Authentication auth) {
        String courseName = taskFilterInfo.getCourseName();
        if(taskFilterInfo.getWeek() == null && taskFilterInfo.getCourseName() == ""){
            return "redirect:/tasks";
        }
        // 해당 기수에 해당하는 TASK만 조회
        if(taskFilterInfo.getCourseName() != "" && taskFilterInfo.getWeek() == null){
            System.out.print("test test");
            redirectAttribute.addAttribute("courseId", courseService.findByCourseName(courseName).getId());
            return "redirect:courses/{courseId}";
        }
        redirectAttribute.addAttribute("week", taskFilterInfo.getWeek());
        redirectAttribute.addAttribute("courseId", courseService.findByCourseName(courseName).getId());
        return "redirect:{courseId}/weeks/{week}";
    }

    @GetMapping("/{courseId}/weeks/{week}")
    public String listWeekAndDay(@PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, @PathVariable long courseId, @PathVariable long week, Authentication auth, Model model){
        UserEntity loginUser = findService.findUserByUserName(auth.getName());
        CourseEntity course = findService.findCourseByUserId(loginUser);
        TaskListResponse res =  findService.getTasksByCourseIdAndWeek(pageable, courseId, week);

        model.addAttribute("taskList", res.getContent());
        model.addAttribute("nowPage", res.getPageable().getPageNumber() + 1);
        model.addAttribute("lastPage", res.getTotalPages());

        List<CourseDto> courses = courseService.courses();
        model.addAttribute("courseList", courses);

        CourseEntity result = courseService.findByCourseId(courseId);
        TaskFilterInfo taskFilterInfo = TaskFilterInfo.builder().week(week).courseName(result.getName()).build();
        model.addAttribute("taskFilterInfo", taskFilterInfo);

        return "tasks/show";
    }


    @GetMapping("/write")
    public String writePage(Model model) {
        model.addAttribute("taskCreateRequest", new TaskCreateRequest());
        List<CourseDto> courses = courseService.courses();
        CourseEntity courseEntity = findService.findCourseByName(courses.get(0).getName());
        model.addAttribute("courseList", courses);
        int day = CourseInfo.fromEntity(courseEntity).getDayOfWeek();
        long week = CourseInfo.fromEntity(courseEntity).getWeek(courseEntity.getStartDate());
        model.addAttribute("week", week);
        model.addAttribute("day", day);
        return "tasks/write";
    }

    @ResponseBody
    @PostMapping("/sendCourse")
    public Map<String, String> courseNameSend(@RequestParam String courseName, Model model){
        CourseEntity courseEntity = findService.findCourseByName(courseName);

        int day = CourseInfo.fromEntity(courseEntity).getDayOfWeek();
        long week = CourseInfo.fromEntity(courseEntity).getWeek(courseEntity.getStartDate());

        Map<String, String> map = new HashMap<>();
        map.put("week", String.valueOf(week));
        map.put("day",String.valueOf(day));
        return map;
    }

    @PostMapping("/write")
    public String write(@ModelAttribute TaskCreateRequest req, Authentication auth) {
        taskService.createTask(req, auth.getName());
        return "redirect:/";
    }

    // task 상세 조회
    @GetMapping("/{taskId}")
    public String show(@PathVariable Long taskId, Model model, Authentication auth) {
        List<CourseDto> courses = courseService.courses();
        CourseEntity courseEntity = findService.findCourseByName(courses.get(0).getName());
        model.addAttribute("courseList", courses);

        TaskDetailResponse res = taskService.getOneTask(taskId);
        List<BoardListDto> boardList = res.getBoards();
        boardList.forEach(boardListDto -> boardListDto.setLikes(likeService.countLikesOfBoard(boardListDto.getId())));
        model.addAttribute("taskUpdateRequest", new TaskUpdateRequest());
        model.addAttribute("taskDetail", res);
        model.addAttribute("boardList", boardList);
        return "tasks/detail";
    }

    @DeleteMapping("/{taskId}/delete")
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
        return "redirect:/tasks";
    }

    @PutMapping("/{taskId}/edit")
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
//        taskService.updateTask(taskId, req, auth.getName());
        model.addAttribute("message", "글이 수정 되었습니다.");
        model.addAttribute("nextUrl", "/tasks/" + taskId);
        return "redirect:/tasks";
    }

}
