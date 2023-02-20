package com.project.feedback.controller.ui;

import com.project.feedback.domain.dto.board.BoardWriteDto;
import com.project.feedback.domain.dto.board.CodeWriteDto;
import com.project.feedback.domain.dto.board.CommentWriteDto;
import com.project.feedback.domain.dto.comment.CommentCreateRequest;
import com.project.feedback.domain.dto.comment.CommentListResponse;
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

    private final BoardService boardService;
    private final CommentService commentService;
    private final CommentsService commentsService;
    private final CodeService codeService;
    private final TaskService taskService;


    @GetMapping("/write")
    public String boardReadWrite(Model model, Authentication auth){
        model.addAttribute("userName",auth.getName());
        return "boards/write";
    }

    @PostMapping("/write")
    public String boardAddWrite(BoardWriteDto boardWriteDto){
        boardService.savePost(boardWriteDto);
        return "redirect:/boards/list";
    }

    // list 제거해도됌
    @GetMapping("/list")
    public String boardWriteList(Model model, Authentication auth){
        List<BoardWriteDto> boardWriteDtoList = boardService.getBoardList();
        model.addAttribute("userName", auth.getName());
        model.addAttribute("boardList", boardWriteDtoList);
        return "boards/list";
    }

    //post 제거
    @GetMapping("/post/{no}")
    public String detail(@PathVariable("no")Long no, Model model){
        BoardWriteDto boardWriteDto = boardService.getPost(no);
        model.addAttribute("boardList", boardWriteDto);
        return "boards/list";
    }

    @GetMapping("/edit/{no}")
    public String edit(@PathVariable("no")Long no, Model model){
        BoardWriteDto boardWriteDto = boardService.getPost(no);

        model.addAttribute("boardList", boardWriteDto);
        return "boards/update";
    }

    @GetMapping("/writeDetail/{no}")
    public String writeDetail(@PathVariable("no")Long no, Model model, Authentication auth){
        BoardWriteDto boardWriteDto = boardService.getPost(no);
        List<CommentWriteDto> commentWriteDtoList = commentService.searchPosts(no);
        model.addAttribute("commentList", commentWriteDtoList);
        model.addAttribute("boardList", boardWriteDto);
        model.addAttribute("userName", auth.getName());
        return "boards/writeDetail";
    }

    @PutMapping("/edit/{no}")
    public String update(BoardWriteDto boardWriteDto, @PathVariable("no")Long no){
        boardService.savePost(boardWriteDto);

        return "redirect:/boards/list";
    }

    @DeleteMapping("/{no}")
    public String delete(@PathVariable("no")Long no){
        boardService.deletePost(no);

        return "redirect:/boards/list";
    }

    /**
     * CodeEntity 관련
     * */
    @GetMapping("/code/view_all")
    public String entireCodeView(Model model){
        List<CodeWriteDto> codeWriteDtoList = codeService.searchAllCode();
        model.addAttribute("codeList", codeWriteDtoList);
        return "boards/code/view_all";
    }

    // taskId에 해당하는 code만 전체 조회 view_one 네이밍 수정
    @GetMapping("/code/{taskId}/view_one")
    public String oneCodeView(@PathVariable("taskId")Long taskId, Model model){
        List<CodeWriteDto> codeWriteDtoList = codeService.getCodeListByTaskId(taskId);
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
        CodeWriteDto codeWriteDto = codeService.getCodeDetail(boardId);
        model.addAttribute("codeInfo", codeWriteDto);

        // 해당 글에 달린 댓글 불러오기
        CommentListResponse res2 = commentsService.getCommentList(boardId, pageable);
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
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        String taskTitle = taskService.getOneTask(taskId).getTitle();
        model.addAttribute("taskTitle", taskTitle);
        model.addAttribute("userName", userName);
        model.addAttribute("taskId", taskId);
        return "boards/code/write";
    }

    @PostMapping("/code/write")
    public String codeAddWrite(CodeWriteDto codeWriteDto, Model model){
        codeService.saveCode(codeWriteDto);
        return "redirect:/boards/code/view_all";
    }


    // 코드 삭제
    @DeleteMapping("/code/view/{boardId}")
    public String codeDelete(@PathVariable("boardId")Long boardId){
        codeService.deleteCode(boardId);
        return "redirect:/boards/code/view_all";
    }

    @PostMapping("/writeDetail/{no}")
    public String commentWrite(@PathVariable("no")Long no,CommentWriteDto commentWriteDto){
        commentService.saveComment(commentWriteDto, no);
        return "redirect:/boards/writeDetail/" + no.toString();
    }

    @DeleteMapping("/writeDetail/{commentId}/{boardId}")
    public String commentDelete(@PathVariable("commentId")Long commentId, @PathVariable("boardId")Long boardId){
        commentService.deletePost(commentId);
        return "redirect:boards/writeDetail/"+boardId.toString();
    }

    @PutMapping("/writeDetail/{commentId}/{boardId}")
    public String commentUpdate(@PathVariable("commentId")Long commentId, CommentWriteDto commentWriteDto, @PathVariable("boardId")Long boardId){
        commentService.updateComment(commentWriteDto, commentId);
        return "redirect:boards/writeDetail/"+boardId.toString();
    }

//    @GetMapping("/codeView")
//    public String codeList(Model model){
//        List<CodeWriteDto> codeWriteDtoList = codeService.searchAllCode();
//        model.addAttribute("codeList", codeWriteDtoList);
//        return "/codeView";
//    }
}
