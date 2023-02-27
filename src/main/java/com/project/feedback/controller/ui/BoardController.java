package com.project.feedback.controller.ui;

import com.project.feedback.domain.dto.board.BoardCreateRequest;
import com.project.feedback.domain.dto.board.BoardListDto;
import com.project.feedback.domain.dto.comment.CommentCreateRequest;
import com.project.feedback.domain.dto.comment.CommentListResponse;
import com.project.feedback.domain.entity.TaskEntity;
import com.project.feedback.domain.entity.UserEntity;
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


    @GetMapping
    public String list(Model model){
        List<BoardListDto> boardList = boardService.searchAllCode();
        model.addAttribute("boardList", boardList);
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
                             @PageableDefault(size = 5, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable)
    {
        BoardListDto boardListDto = boardService.getBoardDetail(boardId);
        model.addAttribute("boardInfo", boardListDto);

        // 해당 글에 달린 댓글 불러오기
        CommentListResponse res2 = commentService.getCommentList(boardId, pageable);
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

    //TASK에 질문 등록
    @GetMapping("/tasks/{taskId}")
    public String writeBoard(@PathVariable("taskId")Long taskId, Model model, Authentication auth) {
        String taskTitle = taskService.getOneTask(taskId).getTitle();

        model.addAttribute("boardCreateRequest", new BoardCreateRequest());
        model.addAttribute("taskTitle", taskTitle);
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
