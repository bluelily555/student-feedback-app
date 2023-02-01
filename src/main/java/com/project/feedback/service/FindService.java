package com.project.feedback.service;

import com.project.feedback.domain.Role;
import com.project.feedback.domain.dto.mainInfo.CourseTaskListResponse;
import com.project.feedback.domain.dto.mainInfo.StatusInfo;
import com.project.feedback.domain.dto.mainInfo.StudentInfo;
import com.project.feedback.domain.dto.mainInfo.TaskInfo;
import com.project.feedback.domain.entity.CourseEntity;
import com.project.feedback.domain.entity.CourseUserEntity;
import com.project.feedback.domain.entity.TaskEntity;
import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.domain.entity.UserTaskEntity;
import com.project.feedback.exception.CustomException;
import com.project.feedback.exception.ErrorCode;
import com.project.feedback.repository.CourseRepository;
import com.project.feedback.repository.CourseUserRepository;
import com.project.feedback.repository.TaskRepository;
import com.project.feedback.repository.UserRepository;
import com.project.feedback.repository.UserTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FindService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final CourseRepository courseRepository;
    private final CourseUserRepository courseUserRepository;
    private final UserTaskRepository userTaskRepository;

    /**
     * userName으로 User을 찾아오는 기능
     * userName에 해당하는 User가 없으면 USERNAME_NOT_FOUND 에러 발생
     * userName으로 찾은 User return
     */
    public UserEntity findUserByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new CustomException(ErrorCode.USERNAME_NOT_FOUND));
    }

    public CourseEntity findCourseByName(String courseName) {
        return courseRepository.findByName(courseName)
            .orElseThrow(() -> new CustomException(ErrorCode.COURSE_NOT_FOUND, String.format("코스명: %s(이)가 없습니다.", courseName)));
    }


    /**
     * 로그인한 유저가 글 작성자이거나 ADMIN인지 체크하는 기능
     * postUser, loginUser 입력
     * 로그인한 유저가 글 작성자이거나 ADMIN이면 true return => 수정, 삭제 가능
     * 로그인한 유저가 권한이 없으면 false return => INVALID_PERMISSION 에러 발생
     */
    public boolean checkAuth(UserEntity postUser, UserEntity loginUser) {
        if(!postUser.getUserName().equals(loginUser.getUserName()) &&
                loginUser.getRole() != Role.ROLE_ADMIN) {
            return false;
        }
        return true;
    }
    public boolean checkAdmin(UserEntity loginUser){
        if(loginUser.getRole() != Role.ROLE_ADMIN){
            return false;
        }
        return true;
    }
    /**
     * taskId로 Task를 찾아오는 기능
     * taskId에 해당하는 Task가 없으면 TASk_NOT_FOUND 에러 발생
     * taskId로 찾은 Task return
     */
    public TaskEntity findTaskById(Long taskId) {
        return taskRepository.findById(taskId)
            .orElseThrow(() -> new CustomException(ErrorCode.TASK_NOT_FOUND));
    }

    /**
     * 로그인한 User(student)가 속한 Course의 학생들 목록을 가져오는 api
     */
    public List<UserEntity> findUserByCourseId(Long courseId, UserEntity loginUser){
        // 로그인한 user가 속한 course_id를 리턴한다.
       CourseUserEntity courseUserEntity = courseUserRepository.findCourseEntityUserByUserId(loginUser.getId())
           .orElseThrow(() -> new CustomException(ErrorCode.USER_COURSE_NOT_FOUND));

       List<CourseUserEntity> courseUserEntities = courseUserRepository.findCourseEntityUserByCourseEntityId(courseUserEntity.getCourseEntity().getId());
       List<UserEntity> users = new ArrayList<>();

       for(CourseUserEntity c : courseUserEntities){
           users.add(c.getUser());
       }

       return users;
    }

    /**
     * course에 등록된 task 목록 조회 API : 필요한 output task명, week, day
     */
     public List<TaskEntity> findTaskByCourseId(Long courseId){
         CourseEntity courses = courseRepository.findById(courseId)
             .orElseThrow(() -> new CustomException(ErrorCode.COURSE_NOT_FOUND));

         List<TaskEntity> taskEntities = courses.getTaskEntities();

         return taskEntities;
     }

    public CourseEntity findCourseByUserId(UserEntity loginUser){
        CourseUserEntity courseUserEntity = courseUserRepository.findCourseEntityUserByUserId(loginUser.getId())
            .orElseThrow(() -> new CustomException(ErrorCode.USER_COURSE_NOT_FOUND));
        return courseUserEntity.getCourseEntity();
    }

    /**
     * 학생이 속한 코스의 테스크들과, 학생이 속한 코스의 다른 학생들 목록이 아래와 같이 json형태로 합쳐보여줄 수 있도록
     * [
     *   tasks:[{"":"", ""}, {"":"", ""}, {"":"", ""}],
     *   students:[{"":"", ""}]
     * ]
     */

    public CourseTaskListResponse getTasksAndStudents(Long courseId, UserEntity loginUser){
        // task setting
        List<TaskEntity> taskEntities = findTaskByCourseId(courseId);
        List<TaskInfo> taskInfoList = new ArrayList<>();
        for(TaskEntity t : taskEntities){
            taskInfoList.add(TaskInfo.of(t));
        }
        // student setting
        List<UserEntity> users = findUserByCourseId(courseId, loginUser);
        List<StudentInfo> studentInfoList = new ArrayList<>();

        for(UserEntity u : users){
            studentInfoList.add(StudentInfo.of(u));
        }

        CourseTaskListResponse courseTaskListResponse =  CourseTaskListResponse.builder()
                                                            .taskInfoList(taskInfoList)
                                                            .studentInfoList(studentInfoList)
                                                            .build();
        return courseTaskListResponse;
    }

    public CourseTaskListResponse getTasksAndStudentsByWeekAndDay(Long courseId, Long week, Long day, UserEntity loginUser){
        //해당 course에 week, day로 필터 걸어서 task 목록을 가져옴
        List<TaskEntity> taskEntities = taskRepository.findByCourseIdAndWeekAndDay(courseId, week, day);

        List<TaskInfo> taskInfoList = new ArrayList<>();
        for(TaskEntity t : taskEntities){
            taskInfoList.add(TaskInfo.of(t));
        }
        // student setting
        List<UserEntity> users = findUserByCourseId(courseId, loginUser);
        List<StudentInfo> studentInfoList = new ArrayList<>();

        //userTask
        for(UserEntity u : users){
            studentInfoList.add(StudentInfo.of(u));
        }

        CourseTaskListResponse courseTaskListResponse =  CourseTaskListResponse.builder()
            .taskInfoList(taskInfoList)
            .studentInfoList(studentInfoList)
            .build();
        return courseTaskListResponse;
    }


    public List<HashMap<String, String>> getStudentsWithTask(Long courseId, Long week, Long day, UserEntity loginUser){
        // course와 week에 해당하는 task목록
        List<TaskEntity> taskEntities = taskRepository.findByCourseIdAndWeekAndDay(courseId, week, day);
        // filter 정보에 해당하는 task id 정보만 저장
        List<Long> ids = new ArrayList<>();
        for(TaskEntity taskEntity : taskEntities){
            ids.add(taskEntity.getId());
        }

        // course에 해당하는 USER 정보 가져오기
        List<UserEntity> users = findUserByCourseId(courseId, loginUser);

        ArrayList<HashMap<String, String>> list = new ArrayList<>();
            for(UserEntity user : users){
                List<UserTaskEntity> userTaskEntities = userTaskRepository.findByUserId(user.getId());
                HashMap<String, String> map = new HashMap<>();
                map.put("studentName", user.getRealName());
                int index = 1;
                for(UserTaskEntity userTaskEntity : userTaskEntities){
                    // task id
                    Long id = userTaskEntity.getTaskEntity().getId();
                    if(ids.contains(id)){
                        map.put("taskId"+index, id.toString());
                        TaskEntity task =  taskRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.TASK_NOT_FOUND));
                        map.put("taskName"+index, task.getTitle());
                        map.put("taskStatus"+index, userTaskEntity.getStatus().toString());
                        index++;
                    }

                }
                list.add(map);
            }

        return list;
    }

    public List<StudentInfo> getStudentsWithTask2(Long courseId, Long week, Long day, UserEntity loginUser){
        // course와 week에 해당하는 task목록
        List<TaskEntity> taskEntities = taskRepository.findByCourseIdAndWeekAndDay(courseId, week, day);
        // filter 정보에 해당하는 task id 정보만 저장
        List<Long> ids = new ArrayList<>();
        for(TaskEntity taskEntity : taskEntities){
            ids.add(taskEntity.getId());
        }

        // course에 해당하는 USER 정보 가져오기
        List<UserEntity> users = findUserByCourseId(courseId, loginUser);

        List<StudentInfo> list = new ArrayList<>();
        for(UserEntity user : users){
            List<StatusInfo> status2 = new ArrayList<>();
            List<UserTaskEntity> userTaskEntities = userTaskRepository.findByUserId(user.getId());
            HashMap<String, String> map = new HashMap<>();
            map.put("studentName", user.getRealName());
            for(TaskEntity taskEntity : taskEntities ){

                // usertask에서 조회해서 가져와야함.
                // taskid, userid로 조회한 status리스트가 있으면 넣어줌.
                UserTaskEntity userTaskEntity = userTaskRepository.findByUserIdAndTaskEntityId(user.getId(), taskEntity.getId());
                if(userTaskEntity != null){
                    status2.add(StatusInfo.of(userTaskEntity.getStatus().toString()));
                }
                else{
                    status2.add(StatusInfo.of("없음"));
                }

            }
            list.add(StudentInfo.of(user, status2));
        }

        return list;
    }



}
