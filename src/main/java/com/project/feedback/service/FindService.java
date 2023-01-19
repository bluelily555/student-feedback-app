package com.project.feedback.service;

import com.project.feedback.domain.Role;
import com.project.feedback.domain.entity.CourseEntity;
import com.project.feedback.domain.entity.TaskEntity;
import com.project.feedback.domain.entity.User;
import com.project.feedback.exception.CustomException;
import com.project.feedback.exception.ErrorCode;
import com.project.feedback.repository.CourseRepository;
import com.project.feedback.repository.TaskRepository;
import com.project.feedback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final CourseRepository courseRepository;

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
}
