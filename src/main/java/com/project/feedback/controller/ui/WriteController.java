package com.project.feedback.controller.ui;

import com.project.feedback.domain.dto.board.BoardWriteDto;
import com.project.feedback.domain.dto.board.CodeWriteDto;
import com.project.feedback.domain.dto.board.CommentWriteDto;
import com.project.feedback.service.BoardService;
import com.project.feedback.service.CodeService;
import com.project.feedback.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/boards")
public class WriteController {

    private BoardService boardService;
    private CommentService commentService;
    private CodeService codeService;


    @GetMapping("/write")
    public String boardReadWrite(){
        return "boards/write";
    }
    @GetMapping("/list")
    public String boardWriteList(Model model){
        List<BoardWriteDto> boardWriteDtoList = boardService.getBoardList();

        model.addAttribute("boardList", boardWriteDtoList);
        return "boards/list";
    }
    @PostMapping("/write")
    public String boardAddWrite(BoardWriteDto boardWriteDto){
        boardService.savePost(boardWriteDto);
        return "redirect:/boards/list";
    }
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
    public String writeDetail(@PathVariable("no")Long no, Model model){
        BoardWriteDto boardWriteDto = boardService.getPost(no);
        List<CommentWriteDto> commentWriteDtoList = commentService.searchPosts(no);
        model.addAttribute("commentList", commentWriteDtoList);
        model.addAttribute("boardList", boardWriteDto);
        return "boards/writeDetail";
    }
    @PutMapping("/edit/{no}")
    public String update(BoardWriteDto boardWriteDto, @PathVariable("no")Long no){
        boardService.savePost(boardWriteDto);

        return "redirect:/boards/list";
    }
    @DeleteMapping("post/{no}")
    public String delete(@PathVariable("no")Long no){
        boardService.deletePost(no);

        return "redirect:/boards/list";
    }
    @GetMapping("/codeView")
    public String codeView(Model model){
        List<CodeWriteDto> codeWriteDtoList = codeService.searchAllCode();

        model.addAttribute("codeList", codeWriteDtoList);
        return "boards/codeView";
    }
    @GetMapping("/codeDetail/{boardId}")
    public String codeDetail(@PathVariable("boardId")Long boardId, Model model){
        CodeWriteDto codeWriteDto = codeService.getCodeDetail(boardId);
        model.addAttribute("codeInfo", codeWriteDto);
        return "/boards/codeDetail";
    }
    @GetMapping("/codeWrite")
    public String codeWrite() { return "boards/codeWrite"; }
    @PostMapping("/codeWrite")
    public String codeAddWrite(CodeWriteDto codeWriteDto){
        codeService.saveCode(codeWriteDto);
        return "redirect:/boards/codeView";
    }
    @DeleteMapping("/codeView/{boardId}")
    public String codeDelete(@PathVariable("boardId")Long boardId){
        codeService.deleteCode(boardId);
        return "redirect:/boards/codeView";
    }
    @PostMapping("/writeDetail/{no}")
    public String commentWrite(@PathVariable("no")Long no,CommentWriteDto commentWriteDto){
        commentService.saveComment(commentWriteDto, no);
        return "redirect:/boards/writeDetail/" + no.toString();
    }
    @DeleteMapping("/writeDetail/{commentId}/{boardId}")
    public String commentDelete(@PathVariable("commentId")Long commentId, @PathVariable("boardId")Long boardId){
        commentService.deletePost(commentId);
        return "redirect:/boards/writeDetail/"+boardId.toString();
    }
    @PutMapping("/writeDetail/{commentId}/{boardId}")
    public String commentUpdate(@PathVariable("commentId")Long commentId, CommentWriteDto commentWriteDto, @PathVariable("boardId")Long boardId){
        commentService.updateComment(commentWriteDto, commentId);
        return "redirect:/boards/writeDetail/"+boardId.toString();
    }

//    @GetMapping("/codeView")
//    public String codeList(Model model){
//        List<CodeWriteDto> codeWriteDtoList = codeService.searchAllCode();
//        model.addAttribute("codeList", codeWriteDtoList);
//        return "/codeView";
//    }
}
