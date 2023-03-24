package com.project.feedback.controller.ui;

import com.project.feedback.domain.dto.board.BoardCreateRequest;
import com.project.feedback.domain.dto.board.BoardListDto;
import com.project.feedback.domain.dto.board.BoardListResponse;
import com.project.feedback.domain.dto.board.BoardModifyRequest;
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
import com.project.feedback.exception.CustomException;
import com.project.feedback.exception.ErrorCode;
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
import org.springframework.web.multipart.MultipartFile;
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
        return getBoardListWithRank(model, res);
    }
    @GetMapping("/search/{title}")
    public String search(@PathVariable("title")String title, Model model, @PageableDefault(size = 20) @SortDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        BoardListResponse res = boardService.searchByTitle(pageable, title);

        model.addAttribute("title", title);

        return getBoardList(model, res);
    }

    private String getBoardListWithRank(Model model, BoardListResponse res) {
        model.addAttribute("noCommentRank", findService.getBoardNoComment());
        model.addAttribute("likeRank", findService.getBoardLikesRank());
        return getBoardList(model, res);
    }

    private String getBoardList(Model model, BoardListResponse res) {
        List<BoardListDto> boardList = res.getContent();
        boardList.forEach(boardListDto -> boardListDto.setLikes(likeService.countLikesOfBoard(boardListDto.getId())));
        boardList.forEach(boardListDto -> boardListDto.setComments(commentService.countCommentsOfBoard(boardListDto.getId())));
        if(res.getContent().size() == 0){
            model.addAttribute("nothing", true);
        }else{
            model.addAttribute("nothing", false);
        }
        model.addAttribute("boardList", boardList);
        model.addAttribute("nullTaskId", 0);
        model.addAttribute("nowPage", res.getPageable().getPageNumber() +1);
        model.addAttribute("lastPage", res.getTotalPages());
        return "boards/show";
    }

    @GetMapping("/tasks/{taskId}/all")
    public String listByTaskId(@PathVariable("taskId")Long taskId, Model model,@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        List<BoardListDto> boardList = boardService.getBoardListByTaskId(pageable, taskId);
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
        model.addAttribute("auth", loginUser);
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

    // 질문 수정 페이지
    @GetMapping("/{boardId}/edit")
    public String editBoardPage(@PathVariable Long boardId, Model model, Authentication auth) {
        // 로그인하지 않은 경우
        if (auth == null) return "redirect:/boards/" + boardId;

        UserEntity loginUser = findService.findUserByUserName(auth.getName());

        BoardEntity board = findService.findByBoardId(boardId);

        // 소유자 확인
        if (!board.equalsOwner(loginUser) && !loginUser.isManager()) return "redirect:/boards/" + boardId;

        CourseEntity course = findService.findCourseByUserId(loginUser);
        TaskEntity task = board.getTaskEntity();
        List<TaskInfo> tasks = findService.getTasksAndStudentsByWeekAndDay(course.getId(), task.getWeek(), task.getDayOfWeek(), loginUser).getTaskInfoList();

        model.addAttribute("boardInfo", BoardListDto.detailOf(board));
        model.addAttribute("tasks", tasks);

        return "boards/edit";
    }

    // 질문 수정
    @PostMapping("/{boardId}/edit")
    public String editBoard(@PathVariable Long boardId, BoardModifyRequest request, MultipartFile file, Authentication auth) {
        // 로그인하지 않은 경우
        if (auth == null) return "redirect:/boards/" + boardId;

        UserEntity loginUser = findService.findUserByUserName(auth.getName());

        BoardEntity board = findService.findByBoardId(boardId);

        // 소유자 확인
        if (!board.equalsOwner(loginUser) && !loginUser.isManager()) return "redirect:/boards/" + boardId;

        boardService.modifyBoard(boardId, request, file);

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
    public String write(@PathVariable("taskId")Long taskId, BoardCreateRequest req, MultipartFile file, Model model, Authentication auth){
        TaskEntity taskEntity = findService.findTaskById(taskId);
        UserEntity loginUser = findService.findUserByUserName(auth.getName());
        boardService.save(req, file, loginUser, taskEntity);
        return "redirect:/boards";
    }


    @DeleteMapping("/{boardId}")
    public String delete(@PathVariable("boardId")Long boardId){
        boardService.delete(boardId);
        return "redirect:/boards";
    }

}
