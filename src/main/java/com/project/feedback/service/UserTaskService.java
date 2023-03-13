package com.project.feedback.service;

import com.project.feedback.domain.TaskStatus;
import com.project.feedback.domain.dto.mainInfo.StatusInfo;
import com.project.feedback.domain.dto.task.TaskListDto;
import com.project.feedback.domain.entity.TaskEntity;
import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.domain.entity.UserTaskEntity;
import com.project.feedback.exception.CustomException;
import com.project.feedback.exception.ErrorCode;
import com.project.feedback.repository.CourseUserRepository;
import com.project.feedback.repository.TaskRepository;
import com.project.feedback.repository.UserTaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserTaskService {

    private final UserTaskRepository userTaskRepository;
    private final CourseUserRepository courseUserRepository;
    private final TaskRepository taskRepository;
    private final TaskService taskService;

    @Transactional
    public void createUserTask(StatusInfo req, UserEntity user){
        TaskEntity taskEntity = taskRepository.findById(req.getTaskId())
            .orElseThrow(() -> new CustomException(ErrorCode.TASK_NOT_FOUND));

        UserTaskEntity userTaskEntity = new UserTaskEntity();
        userTaskEntity.setTaskEntity(taskEntity);
        userTaskEntity.setUser(user);
        userTaskEntity.setStatus(TaskStatus.valueOf(req.getTaskStatus()));

        userTaskRepository.save(userTaskEntity);
    }

    @Transactional
    public void updateUserTask(StatusInfo req, UserEntity user){
        TaskEntity taskEntity = taskRepository.findById(req.getTaskId())
            .orElseThrow(() -> new CustomException(ErrorCode.TASK_NOT_FOUND));

        UserTaskEntity userTaskEntity = userTaskRepository.findByUserIdAndTaskEntityId(user.getId(), req.getTaskId());
        userTaskEntity.setTaskEntity(taskEntity);
        userTaskEntity.setUser(user);
        userTaskEntity.setStatus(TaskStatus.valueOf(req.getTaskStatus()));

        userTaskRepository.save(userTaskEntity);
    }

    @Transactional
    public void updateUserTask(Long taskId, Long userId, String status){
        UserTaskEntity userTaskEntity = userTaskRepository.findByUserIdAndTaskEntityId(userId, taskId);

        userTaskEntity.setStatus(TaskStatus.valueOf(status));
        userTaskRepository.save(userTaskEntity);
    }

    /**
     *  task별 진도율 태스크별 진도율 = 완료학생 수 / 전체 학생 수 * 100%
     */
    public String progressPercentageByTask(Long taskId, Long courseId){
         //완료인 학생 수 count 쿼리 : userTask에서 taskId로 조회하고, 그 중에서 status가 done인 것 count
         Long countOfDone = userTaskRepository.countByTaskEntityIdAndStatus(taskId);
         //전체 학생 수 count 쿼리
         Long countOfStudents = courseUserRepository.countByCourseEntityId(courseId);

         double num = ((double)countOfDone / (double)countOfStudents ) * 100.0;
         double result = Math.round(num);
         String output = String.format("%.0f", result);
         return output;
    }


    /**
     * 개인 진도율 = 완료한 태스크 수 / 전체 태스크 수
     * */
    public Map<String, Long> progressPercentageByUser(Long userId, Long week, Long day){
       // 해당 week, day에 전체 테스크 수
        Map<String, Long> map = new HashMap<>();
        List<Long> taskIdList = new ArrayList<>();
        List<TaskListDto> taskListDtos = taskService.getTaskListByWeekAndDay(week, day);

        long totalCountOfTask = taskListDtos.size();
        map.put("totalTask", totalCountOfTask);
        for(int i =0; i < totalCountOfTask; i++){
            taskIdList.add(taskListDtos.get(i).getId());
        }

        long countsOfDone = 0;
        for (int i = 0; i < taskIdList.size(); i++){
            if(userTaskRepository.findByUserIdAndTaskEntityId(userId, taskIdList.get(i)) !=null){
                if(userTaskRepository.findByUserIdAndTaskEntityId(userId, taskIdList.get(i)).getStatus().toString() == "DONE")
                    countsOfDone += 1;
            }
        }

        map.put("totalTaskOfStudent", countsOfDone);

        return map;
    }
    @Transactional
    public List<UserTaskEntity>getAllTaskByUserId(Long userId){
        List<UserTaskEntity> userTaskEntityList = userTaskRepository.findAllByUserId(userId);
        for(UserTaskEntity userTaskEntity: userTaskEntityList){
            log.info("userId 로 찾은 데이터들" + userTaskEntity.getTaskEntity().getTitle());
        }
        return userTaskRepository.findAllByUserId(userId);
    }
}
