package com.project.feedback.application;

import com.project.feedback.application.entity.CourseStudent;
import com.project.feedback.application.entity.Task;
import com.project.feedback.application.entity.User;
import com.project.feedback.application.entity.UserTask;
import com.project.feedback.domain.Role;
import com.project.feedback.domain.TaskStatus;
import com.project.feedback.domain.dto.board.BoardListDto;
import com.project.feedback.domain.dto.mainInfo.CourseTaskListResponse;
import com.project.feedback.domain.dto.mainInfo.StatusInfo;
import com.project.feedback.domain.dto.mainInfo.StudentInfo;
import com.project.feedback.domain.dto.mainInfo.TaskInfo;
import com.project.feedback.domain.dto.task.TaskListDto;
import com.project.feedback.domain.dto.task.TaskListResponse;
import com.project.feedback.infra.outgoing.jpa.BoardEntity;
import com.project.feedback.infra.outgoing.jpa.CommentEntity;
import com.project.feedback.infra.outgoing.adapter.TaskAdapter;
import com.project.feedback.infra.outgoing.adapter.UserTaskAdapter;
import com.project.feedback.infra.outgoing.jpa.CourseEntity;
import com.project.feedback.infra.outgoing.jpa.CourseUserEntity;
import com.project.feedback.infra.outgoing.jpa.RepositoryEntity;
import com.project.feedback.infra.outgoing.jpa.TaskEntity;
import com.project.feedback.infra.outgoing.jpa.TokenEntity;
import com.project.feedback.infra.outgoing.jpa.UserEntity;
import com.project.feedback.infra.outgoing.jpa.UserTaskEntity;
import com.project.feedback.domain.enums.LikeContentType;
import com.project.feedback.exception.CustomException;
import com.project.feedback.exception.ErrorCode;
import com.project.feedback.infra.outgoing.repository.*;
import com.project.feedback.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class FindService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final CourseRepository courseRepository;
    private final CourseUserRepository courseUserRepository;
    private final BoardRepository boardRepository;
    private final UserTaskRepository userTaskRepository;
    private final TokenRepository tokenRepository;
    private final CommentRepository commentRepository;
    private final RepositoryRepository repositoryRepository;
    private final LikeRepository likeRepository;

    private final TaskAdapter taskAdapter;
    private final UserTaskAdapter userTaskAdapter;

    /**
     * userName으로 User을 찾아오는 기능
     * userName에 해당하는 User가 없으면 USERNAME_NOT_FOUND 에러 발생
     * userName으로 찾은 User return
     */
    public UserEntity findUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.EMAIL_NOT_FOUND));
    }
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

    public boolean checkUserTask(Long userId, Long taskId){
        UserTaskEntity task = userTaskRepository.findByUserIdAndTaskEntityId(userId, taskId);
        if(task != null){
            return true;
        }
        return false;
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
     * 로그인 한 user의 course 리턴
     **/
    @ModelAttribute("courseId")
    public CourseEntity findCourseByUser(UserEntity loginUser){
        CourseUserEntity courseUserEntity = courseUserRepository.findCourseEntityUserByUserId(loginUser.getId())
            .orElseThrow(() -> new CustomException(ErrorCode.USER_COURSE_NOT_FOUND));

        return courseUserEntity.getCourseEntity();
    }
    /**
     * 로그인한 User(student)가 속한 Course의 학생들 목록을 가져오는 api
     */
    public List<UserEntity> findUserByCourseId(Long courseId, UserEntity loginUser){
        // 로그인한 user가 속한 course_id를 리턴한다.
       CourseUserEntity courseUserEntity = courseUserRepository.findCourseEntityUserByUserId(loginUser.getId())
           .orElseThrow(() -> new CustomException(ErrorCode.USER_COURSE_NOT_FOUND));

       List<CourseUserEntity> courseUserEntities = courseUserRepository.findCourseEntityUserByCourseEntityId(courseUserEntity.getCourseEntity().getId());
       Collections.sort(courseUserEntities, new Comparator<CourseUserEntity>() {
           @Override
           public int compare(CourseUserEntity o1, CourseUserEntity o2) {
               return 0;
           }
       });

       List<UserEntity> users = new ArrayList<>();

       for(CourseUserEntity c : courseUserEntities){
           UserEntity userEntity = c.getUser();
           // 학생만 추가
           if(userEntity.getRole().equals(Role.ROLE_STUDENT)){
               users.add(userEntity);
           }
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
            .orElseThrow(() -> new CustomException(ErrorCode.USER_COURSE_NOT_FOUND, String.format("%s가 속한 코스가 없습니다.", loginUser.getUserName())));
        return courseUserEntity.getCourseEntity();
    }

    public List<CourseEntity> findAllCourse() {
         return courseRepository.findAll();
    }

    /**
     * 학생이 속한 코스의 테스크들과, 학생이 속한 코스의 다른 학생들 목록이 아래와 같이 json형태로 합쳐보여줄 수 있도록
     * [
     *   tasks:[{"":"", ""}, {"":"", ""}, {"":"", ""}],
     *   students:[{"":"", ""}]
     * ]
     */
    @Cacheable(value = "get_tasks_and_students", key = "#courseId + '_' + #loginUser.id")
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


    public TaskListResponse getTasksByCourseIdAndWeek(Pageable pageable, Long courseId, Long week){
        //courseId, week로 task 목록 호출
        Page<TaskEntity> taskEntities = taskRepository.findByWeekAndCourseEntityId(pageable, week, courseId);
        List<TaskListDto> content = new ArrayList<>();
          for(TaskEntity task : taskEntities) {
              content.add(TaskListDto.of(task));
          }
       return new TaskListResponse(content, pageable, taskEntities);
    }

//    @Cacheable(value = "course_student", key = "#courseId + '-' + #week + '-' + #day + '-' + #loginUser.userName")
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

    @Cacheable(value = "get_students_with_task", key = "#courseId + '_' + #week + '_' + #day")
    public List<StudentInfo> getStudentsWithTask(Long courseId, Long week, Long day, UserEntity loginUser){

        // course와 week에 해당하는 task목록
        List<TaskEntity> taskEntities = taskRepository.findByCourseIdAndWeekAndDay(courseId, week, day);

        // course에 해당하는 USER 정보 가져오기
        List<UserEntity> userEntities = findUserByCourseId(courseId, loginUser).stream()
                .sorted(Comparator.comparing(UserEntity::getRealName))
                .collect(Collectors.toList());

        // CourseStudent Domain사용
        CourseStudent courseStudent = new CourseStudent(taskEntities, userEntities);

        List<UserTaskEntity> userTaskEntities = userTaskAdapter.findByUserIdIn(courseStudent.getUserIds());
        List<UserTask> userTasks = userTaskEntities.stream()
                .map(userTaskEntity -> UserTask.builder()
                        .id(userTaskEntity.getId())
                        .userId(userTaskEntity.getUser().getId())
                        .taskId(userTaskEntity.getTaskEntity().getId())
                        .build())
                .collect(Collectors.toList());

        courseStudent.setUserTasks(userTasks);

        int taskSize = taskEntities.size();

        List<StudentInfo> studentInfoList = new ArrayList<>(); // 결과
        for(UserEntity user : userEntities){

            List<Long> userIds = new ArrayList(
                Collections.nCopies(taskSize, user.getId())
            );

            //데이터 셋팅
            List<StatusInfo> statusInfo = taskEntities.stream()
                    .map(taskEntity ->{
                        // filter 정보에 해당하는 task id 정보만 저장
                        UserTaskEntity userTask = userTaskEntities.stream()
                                .filter(userTaskEntity -> userTaskEntity.getUser().getId() == user.getId() && userTaskEntity.getTaskEntity().getId() == taskEntity.getId())
                                .findFirst()
                                .orElse(UserTaskEntity.builder()
                                        .status(TaskStatus.CREATED)
                                        .build());
                        return StatusInfo.of(taskEntity.getId(), taskEntity.getTitle(), userTask.getStatus().toString());
                    })
                    .collect(Collectors.toList());


            studentInfoList.add(StudentInfo.of(user, statusInfo));
        }

        if (loginUser.getRole() != Role.ROLE_STUDENT) return studentInfoList;

        for (int i = 0; i < studentInfoList.size(); i++) {
            if (studentInfoList.get(i).getUserName().equals(loginUser.getUserName())) {
                studentInfoList.add(0, studentInfoList.remove(i));
                break;
            }
        }

        return studentInfoList;

    }

    public BoardEntity findByBoardId(long boardId){
       return boardRepository.findById(boardId).orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));
    }

    public CommentEntity findByCommentId(long commentId){
       return commentRepository.findById(commentId).orElseThrow(() -> new CustomException(ErrorCode.COMMENTS_NOT_FOUND));
    }
    // access token 찾기
    public TokenEntity findTokenByCurrentToken(String token){
        return tokenRepository.findByAccessToken(token)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_TOKEN));
    }

    public List<RepositoryEntity> findRepositoriesByUser(UserEntity user) {
        return repositoryRepository.findByUserOrderByModifiedDateDesc(user);
    }

    // Distinct Repository Name
    public Set<String> findAllRepositoryNames() {
        return repositoryRepository.findAll().stream()
                .map(RepositoryEntity::getName)
                .collect(Collectors.toSet());
    }

    public List<RepositoryEntity> findRepositoryByName(String name) {
        return repositoryRepository.findByName(name);
    }

    @Cacheable(value = "get_no_comment_board")
    public List<BoardListDto> getBoardNoComment() {
        return boardRepository.findTop10ByCommentsIsNullOrderByIdDesc().stream()
                .map(BoardListDto::shortOf)
                .map(this::countBoardLikes)
                .toList();
    }

    @Cacheable(value = "get_board_likes_rank")
    public List<BoardListDto> getBoardLikesRank() {
        List<Long> boardIds = likeRepository.findLikesOfBoardRank(PageRequest.of(0, 10));
        return boardRepository.findByIdInFetch(boardIds).stream()
                .map(BoardListDto::shortOf)
                .map(this::countBoardLikes)
                .sorted((b1, b2) -> {
                    if (b1.getLikes() == b2.getLikes()) return b2.getCreatedDate().compareTo(b1.getCreatedDate());
                    return b2.getLikes() - b1.getLikes();
                })
                .toList();
    }

    private BoardListDto countBoardLikes(BoardListDto board) {
        board.setLikes(likeRepository.countByContentTypeAndContentIdAndStatusIsTrue(LikeContentType.BOARD, board.getId()));
        return board;
    }
}
