package com.project.feedback.controller.ui;

import com.project.feedback.domain.dto.board.BoardCreateRequest;
import com.project.feedback.domain.dto.board.BoardListDto;
import com.project.feedback.domain.dto.board.BoardListResponse;
import com.project.feedback.domain.dto.comment.CommentCreateRequest;
import com.project.feedback.domain.dto.comment.CommentListResponse;
import com.project.feedback.domain.dto.course.CourseInfo;
import com.project.feedback.domain.dto.mainInfo.CourseTaskListResponse;
import com.project.feedback.domain.dto.mainInfo.FilterInfo;
import com.project.feedback.domain.dto.mainInfo.StudentInfo;
import com.project.feedback.domain.dto.mainInfo.TaskInfo;
import com.project.feedback.domain.entity.*;
import com.project.feedback.domain.enums.LikeContentType;
import com.project.feedback.domain.enums.NotificationType;
import com.project.feedback.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
@AllArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final CommentService commentService;
    private final BoardService boardService;
    private final TaskService taskService;
    private final FindService findService;
    private final LikeService likeService;
    private final UserTaskService userTaskService;
    private final NotificationService notificationService;


    @GetMapping
    public String list(Model model, @PageableDefault(size = 20) @SortDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        BoardListResponse res = boardService.searchAllCode(pageable);
        List<BoardListDto> boardList = res.getContent();
        boardList.forEach(boardListDto -> boardListDto.setLikes(likeService.countLikesOfBoard(boardListDto.getId())));

        model.addAttribute("boardList", boardList);
        model.addAttribute("nullTaskId", 0);
        model.addAttribute("nowPage", res.getPageable().getPageNumber() +1);
        model.addAttribute("lastPage", res.getTotalPages());

        return "boards/show";
    }

    @GetMapping("/tasks/{taskId}/all")
    public String listByTaskId(@PathVariable("taskId")Long taskId, Model model){
        List<BoardListDto> boardList = boardService.getBoardListByTaskId(taskId);
        String taskTitle = taskService.getOneTask(taskId).getTitle();
        model.addAttribute("taskTitle", taskTitle);
        model.addAttribute("boardList", boardList);
        return "boards/tasks/show";
    }

    @GetMapping("/{boardId}")
    public String getBoard(@PathVariable("boardId")Long boardId, Model model,
                             @PageableDefault(size = 5, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable,
                           Authentication auth)
    {
        BoardListDto boardListDto = boardService.getBoardDetail(boardId);
        model.addAttribute("boardInfo", boardListDto);
        UserEntity loginUser = auth != null ? findService.findUserByUserName(auth.getName()) : null;
        if (loginUser != null) {
            model.addAttribute("isLiked", likeService.verifyLikeStatusOfBoard(boardId, loginUser));
        }

        // 해당 글에 달린 댓글 불러오기
        CommentListResponse res2 = commentService.getCommentList(boardId, pageable);
        if (loginUser != null) {
            res2.getContent().forEach(commentListDto ->
                    commentListDto.setLikeStatus(likeService.verifyLikeStatusOfComment(commentListDto.getId(), loginUser)));
        }
        model.addAttribute("commentList", res2.getContent());
        model.addAttribute("commentSize", res2.getTotalElements());

        // 댓글 페이징 정보
        model.addAttribute("nowPage", res2.getPageable().getPageNumber() + 1);
        model.addAttribute("lastPage", res2.getTotalPages());

        //comment 객체
        CommentCreateRequest commentCreateRequest = new CommentCreateRequest();
        model.addAttribute("commentCreateRequest", commentCreateRequest);

        return "boards/detail";
    }

    @PostMapping("/{boardId}/comment")
    public String writeComment(@PathVariable Long boardId, @ModelAttribute CommentCreateRequest req,
                        Authentication auth) {
        UserEntity loginUser = findService.findUserByUserName(auth.getName());
        BoardEntity board = findService.findByBoardId(boardId);

        commentService.saveComment(req, auth.getName(), boardId);

        notificationService.create(NotificationType.COMMENT, board.getId(), board.getUser(), loginUser);

        return "redirect:/boards/" + boardId;
    }

    @PostMapping("/{boardId}/like")
    public String likeBoard(@PathVariable Long boardId, Authentication auth) {
        // 로그인하지 않은 경우
        if (auth == null) return "redirect:/boards/" + boardId;

        UserEntity loginUser = findService.findUserByUserName(auth.getName());
        BoardEntity board = findService.findByBoardId(boardId);

        likeService.like(LikeContentType.BOARD, boardId, loginUser);

        notificationService.create(NotificationType.LIKE_BOARD, board.getId(), board.getUser(), loginUser);

        return "redirect:/boards/" + boardId;
    }

    @PostMapping("/{boardId}/unlike")
    public String unlikeBoard(@PathVariable Long boardId, Authentication auth) {
        // 로그인하지 않은 경우
        if (auth == null) return "redirect:/boards/" + boardId;

        UserEntity loginUser = findService.findUserByUserName(auth.getName());
        findService.findByBoardId(boardId);

        likeService.unlike(LikeContentType.BOARD, boardId, loginUser);

        return "redirect:/boards/" + boardId;
    }

    //TASK에 질문 등록
    @PostMapping("/tasks/{taskId}/check")
    public String daysCheck(@PathVariable("taskId")Long taskId, @ModelAttribute FilterInfo filterInfo, RedirectAttributes redirectAttribute, Authentication auth){
        redirectAttribute.addAttribute("week", filterInfo.getWeek());
        redirectAttribute.addAttribute("day", filterInfo.getDay());
        redirectAttribute.addAttribute("taskId", taskId);
        return "redirect:/boards/tasks/{taskId}/weeks/{week}/days/{day}";
    }
    @GetMapping("/tasks/{taskId}")
    public String taskWrite(@PathVariable(required = false)Long taskId, RedirectAttributes redirectAttribute, Authentication auth){
        UserEntity loginUser = findService.findUserByUserName(auth.getName());
        CourseEntity course = findService.findCourseByUserId(loginUser);
        CourseInfo courseInfo = CourseInfo.fromEntity(course);
        FilterInfo filterInfo = FilterInfo.builder()
                .day(Long.valueOf(courseInfo.getWeek()))
                .week(Long.valueOf(courseInfo.getDayOfWeek()))
                .build();

        redirectAttribute.addAttribute("week", courseInfo.getWeek());
        redirectAttribute.addAttribute("day", courseInfo.getDayOfWeek());
        redirectAttribute.addAttribute("taskId", taskId);
        return "redirect:/boards/tasks/{taskId}/weeks/{week}/days/{day}";
    }
    @GetMapping("/tasks/{taskId}/weeks/{week}/days/{day}")
    public String writeBoard(@PathVariable(required = false)Long taskId, @PathVariable(required = false)Long week, @PathVariable(required = false)Long day, Model model, Authentication auth) {
        UserEntity loginUser = findService.findUserByUserName(auth.getName());
        CourseEntity course = findService.findCourseByUserId(loginUser);
        CourseTaskListResponse res =  findService.getTasksAndStudentsByWeekAndDay(course.getId(), week, day, loginUser);
        List<TaskInfo> taskList = res.getTaskInfoList();

        model.addAttribute("filterInfo", new FilterInfo());
        model.addAttribute("boardCreateRequest", new BoardCreateRequest());
        model.addAttribute("taskList", taskList);
        model.addAttribute("userName", auth.getName());
        model.addAttribute("courseName", course.getName());
        model.addAttribute("taskId", taskId);
        model.addAttribute("week", week);
        model.addAttribute("day", day);

        return "boards/write";

    }

    //TASK에 질문 등록
    @PostMapping("/tasks/{taskId}")
    public String write(@PathVariable("taskId")Long taskId, BoardCreateRequest req, Model model, Authentication auth){
        TaskEntity taskEntity = findService.findTaskById(taskId);
        UserEntity loginUser = findService.findUserByUserName(auth.getName());
        boardService.save(req, loginUser, taskEntity);
        return "redirect:/boards";
    }


    @DeleteMapping("/{boardId}")
    public String delete(@PathVariable("boardId")Long boardId){
        boardService.delete(boardId);
        return "redirect:/boards";
    }

}
