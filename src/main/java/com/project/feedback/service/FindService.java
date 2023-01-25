package com.project.feedback.service;

import com.project.feedback.domain.Role;
import com.project.feedback.domain.dto.mainInfo.CourseTaskListResponse;
import com.project.feedback.domain.dto.mainInfo.StudentInfo;
import com.project.feedback.domain.dto.mainInfo.TaskInfo;
import com.project.feedback.domain.entity.CourseEntity;
import com.project.feedback.domain.entity.CourseEntityUser;
import com.project.feedback.domain.entity.TaskEntity;
import com.project.feedback.domain.entity.User;
import com.project.feedback.exception.CustomException;
import com.project.feedback.exception.ErrorCode;
import com.project.feedback.repository.CourseRepository;
import com.project.feedback.repository.CourseUserRepository;
import com.project.feedback.repository.TaskRepository;
import com.project.feedback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FindService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final CourseRepository courseRepository;
    private final CourseUserRepository courseUserRepository;

    /**
     * userName으로 User을 찾아오는 기능
     * userName에 해당하는 User가 없으면 USERNAME_NOT_FOUND 에러 발생
     * userName으로 찾은 User return
     */
    public User findUserByUserName(String userName) {
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
    public boolean checkAuth(User postUser, User loginUser) {
        if(!postUser.getUserName().equals(loginUser.getUserName()) &&
                loginUser.getRole() != Role.ROLE_ADMIN) {
            return false;
        }
        return true;
    }
    public boolean checkAdmin(User loginUser){
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
    public List<User> findUserByCourseId(Long courseId, User loginUser){
        // 로그인한 user가 속한 course_id를 리턴한다.
       CourseEntityUser courseEntityUser = courseUserRepository.findCourseEntityUserByUserId(loginUser.getId())
           .orElseThrow(() -> new CustomException(ErrorCode.USER_COURSE_NOT_FOUND));

       List<CourseEntityUser> courseEntityUsers = courseUserRepository.findCourseEntityUserByCourseEntityId(courseEntityUser.getCourseEntity().getId());
       List<User> users = new ArrayList<>();

       for(CourseEntityUser c : courseEntityUsers){
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

    /**
     * 학생이 속한 코스의 테스크들과, 학생이 속한 코스의 다른 학생들 목록이 아래와 같이 json형태로 합쳐보여줄 수 있도록
     * [
     *   tasks:[{"":"", ""}, {"":"", ""}, {"":"", ""}],
     *   students:[{"":"", ""}]
     * ]
     */

    public CourseTaskListResponse getTasksAndStudents(Long courseId, User loginUser){
        // task setting
        List<TaskEntity> taskEntities = findTaskByCourseId(courseId);
        List<TaskInfo> taskInfoList = new ArrayList<>();
        for(TaskEntity t : taskEntities){
            taskInfoList.add(TaskInfo.of(t));
        }
        // student setting
        List<User> users = findUserByCourseId(courseId, loginUser);
        List<StudentInfo> studentInfoList = new ArrayList<>();

        for(User u : users){
            studentInfoList.add(StudentInfo.of(u));
        }

        CourseTaskListResponse courseTaskListResponse =  CourseTaskListResponse.builder()
                                                            .taskInfoList(taskInfoList)
                                                            .studentInfoList(studentInfoList)
                                                            .build();
        return courseTaskListResponse;
    }

    public CourseTaskListResponse getTasksAndStudentsByWeekAndDay(Long courseId, Long week, Long day, User loginUser){
        //해당 course에 week, day로 필터 걸어서 가져옴
        List<TaskEntity> taskEntities = taskRepository.findByCourseIdAndWeekAndDay(courseId, week, day);

        List<TaskInfo> taskInfoList = new ArrayList<>();
        for(TaskEntity t : taskEntities){
            taskInfoList.add(TaskInfo.of(t));
        }
        // student setting
        List<User> users = findUserByCourseId(courseId, loginUser);
        List<StudentInfo> studentInfoList = new ArrayList<>();

        for(User u : users){
            studentInfoList.add(StudentInfo.of(u));
        }

        CourseTaskListResponse courseTaskListResponse =  CourseTaskListResponse.builder()
            .taskInfoList(taskInfoList)
            .studentInfoList(studentInfoList)
            .build();
        return courseTaskListResponse;
    }

}
