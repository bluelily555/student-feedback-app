package com.project.feedback.service;

import com.project.feedback.domain.dto.task.TaskCreateRequest;
import com.project.feedback.domain.dto.task.TaskCreateResponse;
import com.project.feedback.domain.dto.task.TaskDeleteResponse;
import com.project.feedback.domain.dto.task.TaskDetailResponse;
import com.project.feedback.domain.dto.task.TaskListDto;
import com.project.feedback.domain.dto.task.TaskListResponse;
import com.project.feedback.domain.dto.task.TaskUpdateRequest;
import com.project.feedback.domain.dto.task.TaskUpdateResponse;
import com.project.feedback.domain.entity.CourseEntity;
import com.project.feedback.domain.entity.TaskEntity;
import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.exception.CustomException;
import com.project.feedback.exception.ErrorCode;
import com.project.feedback.repository.TaskRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final FindService findService;

    public TaskCreateResponse createTask(TaskCreateRequest req, String userName) {

        // UserName이 존재하는지 체크
        UserEntity writeUser = findService.findUserByUserName(userName);
        CourseEntity courseEntity = findService.findCourseByName(req.getCourseName());
        // 태스크  등록 시 Authentication에서 User을 꺼내와 Post Entity에 넣어줌
        TaskEntity savedPost = taskRepository.save(req.toEntity(writeUser, courseEntity));

        return TaskCreateResponse.of(savedPost.getId(), savedPost.getTitle());
    }

    public TaskDetailResponse getOneTask(Long taskId) {
        TaskEntity findPost = findService.findTaskById(taskId);
        return TaskDetailResponse.of(findPost);
    }

    public TaskUpdateResponse updateTask(Long taskId, TaskUpdateRequest req, String userName) {
        TaskEntity findTask = findService.findTaskById(taskId);

        // UserName이 존재하는지 체크
        UserEntity postUser = findService.findUserByUserName(findTask.getUser().getUserName());
        UserEntity loginUser = findService.findUserByUserName(userName);
        CourseEntity courseEntity = findService.findCourseByName(req.getCourseName());
        // 현재 로그인한 유저가 글 작성자가 아니고 ADMIN도 아니라면 에러 발생
        if(!findService.checkAuth(postUser, loginUser)) {
            throw new CustomException(ErrorCode.INVALID_PERMISSION);
        }

        // 수정
        findTask.update(req.getTitle(),courseEntity, req.getDescription(), req.getTaskStatus(),req.getWeek(), req.getDay());
        log.info(findTask.getTitle());
        findTask = taskRepository.save(findTask);

        return TaskUpdateResponse.of(findTask.getId());
    }

    public TaskDeleteResponse deleteTask(Long taskId, String userName) {
        TaskEntity findTask = findService.findTaskById(taskId);

        // UserName이 존재하는지 체크
        UserEntity postUser = findService.findUserByUserName(findTask.getUser().getUserName());
        UserEntity loginUser = findService.findUserByUserName(userName);

        // 현재 로그인한 유저가 태스크 작성자가 아니고 ADMIN도 아니라면 에러 발생
        if(!findService.checkAuth(postUser, loginUser)) {
            throw new CustomException(ErrorCode.INVALID_PERMISSION);
        }

        Long deleteId = findTask.getId();
        taskRepository.deleteById(deleteId);

        return TaskDeleteResponse.of(deleteId);
    }

    public TaskListResponse getTaskList(Pageable pageable) {
        Page<TaskEntity> tasks = taskRepository.findAll(pageable);

        List<TaskListDto> content = new ArrayList<>();
        for(TaskEntity task : tasks) {
            content.add(TaskListDto.of(task));
        }

        return new TaskListResponse(content, pageable, tasks);
    }


    /**
     * TO DO
     * */
    public List<TaskListDto> getTaskListByWeekAndDay(Long week, Long day){
        List<TaskEntity> tasks = taskRepository.findByWeekAndDayOfWeek(week, day);

        List<TaskListDto> content = new ArrayList<>();
        for(TaskEntity task : tasks) {
            content.add(TaskListDto.of(task));
        }
        return content;
    }

}
