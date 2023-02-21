package com.project.feedback.controller.ui;

import com.project.feedback.domain.dto.board.BoardCreateRequest;
import com.project.feedback.domain.dto.board.BoardWriteDto;
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
import org.springframework.security.core.context.SecurityContextHolder;
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


    /**
     * CodeEntity 관련
     * */
    @GetMapping("/code/view_all")
    public String entireCodeView(Model model){
        List<BoardWriteDto> codeWriteDtoList = boardService.searchAllCode();
        model.addAttribute("codeList", codeWriteDtoList);
        return "boards/code/view_all";
    }

    // taskId에 해당하는 code만 전체 조회 view_one 네이밍 수정
    @GetMapping("/code/{taskId}/view_one")
    public String oneCodeView(@PathVariable("taskId")Long taskId, Model model){
        List<BoardWriteDto> codeWriteDtoList = boardService.getCodeListByTaskId(taskId);
        String taskTitle = taskService.getOneTask(taskId).getTitle();
        model.addAttribute("taskTitle", taskTitle);
        model.addAttribute("codeList", codeWriteDtoList);
        return "boards/code/view_one";
    }

    //codeId, boardId 혼용되어 사용지 코드 상세 보기 -> boards/{boardId}로 수정 필요
    @GetMapping("/code/detail/{boardId}")
    public String codeDetail(@PathVariable("boardId")Long boardId, Model model,
                             @PageableDefault(size = 5, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable)
    {
        BoardWriteDto codeWriteDto = boardService.getCodeDetail(boardId);
        model.addAttribute("codeInfo", codeWriteDto);

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

        return "boards/code/detail";
    }

    // 특정 task에 대해 "질문하러 가기" 버튼 / task쪽으로 옮기는게 좋을 듯
    @GetMapping("/code/{taskId}/write")
    public String codeWrite(@PathVariable("taskId")Long taskId, Model model, Authentication auth) {
       // UserEntity loginUser = findService.findUserByUserName(auth.getName());
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        String taskTitle = taskService.getOneTask(taskId).getTitle();

        model.addAttribute("boardCreateRequest", new BoardCreateRequest());
        model.addAttribute("taskTitle", taskTitle);
        model.addAttribute("userName", userName);
        model.addAttribute("taskId", taskId);

        return "boards/code/write";
    }

    @PostMapping("/code/{taskId}/write")
    public String codeAddWrite(@PathVariable("taskId")Long taskId, BoardCreateRequest codeWriteDto, Model model, Authentication auth){
        TaskEntity taskEntity = findService.findTaskById(taskId);
        UserEntity loginUser = findService.findUserByUserName(auth.getName());
        boardService.saveCode(codeWriteDto, loginUser, taskEntity);
        return "redirect:/boards/code/view_all";
    }


    // 코드 삭제
    @DeleteMapping("/code/view/{boardId}")
    public String codeDelete(@PathVariable("boardId")Long boardId){
        boardService.deleteCode(boardId);
        return "redirect:/boards/code/view_all";
    }



//    @GetMapping("/codeView")
//    public String codeList(Model model){
//        List<CodeWriteDto> codeWriteDtoList = codeService.searchAllCode();
//        model.addAttribute("codeList", codeWriteDtoList);
//        return "/codeView";
//    }
}
