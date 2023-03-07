package com.project.feedback.controller.ui;

import com.project.feedback.domain.dto.board.BoardCreateRequest;
import com.project.feedback.domain.dto.board.BoardListDto;
import com.project.feedback.domain.dto.comment.CommentCreateRequest;
import com.project.feedback.domain.dto.comment.CommentListResponse;
import com.project.feedback.domain.entity.TaskEntity;
import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.domain.entity.UserTaskEntity;
import com.project.feedback.domain.enums.LikeContentType;
import com.project.feedback.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping
    public String list(Model model){
        List<BoardListDto> boardList = boardService.searchAllCode();
        boardList.forEach(boardListDto -> boardListDto.setLikes(likeService.countLikesOfBoard(boardListDto.getId())));
        model.addAttribute("boardList", boardList);
        model.addAttribute("nullTaskId", 0);
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
    public String write(@PathVariable Long boardId, @ModelAttribute CommentCreateRequest req,
                        Authentication auth) {
        commentService.saveComment(req, auth.getName(), boardId);
        return "redirect:/boards";
    }

    @PostMapping("/{boardId}/like")
    public String likeBoard(@PathVariable Long boardId, Authentication auth) {
        // 로그인하지 않은 경우
        if (auth == null) return "redirect:/boards/" + boardId;

        UserEntity loginUser = findService.findUserByUserName(auth.getName());
        findService.findByBoardId(boardId);

        likeService.like(LikeContentType.BOARD, boardId, loginUser);

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
    @GetMapping("/tasks/{taskId}")
    public String writeBoard(@PathVariable(required = false)Long taskId, Model model, Authentication auth) {
        Long userId = findService.findUserByUserName(auth.getName()).getId();
        List<UserTaskEntity> userTaskEntityList = userTaskService.getAllTaskByUserId(userId);
        model.addAttribute("boardCreateRequest", new BoardCreateRequest());
        model.addAttribute("taskList", userTaskEntityList);
        model.addAttribute("userName", auth.getName());
        model.addAttribute("taskId", taskId);

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
