package com.project.feedback.controller.ui;

import com.project.feedback.domain.dto.board.BoardWriteDto;
import com.project.feedback.domain.dto.board.CommentWriteDto;
import com.project.feedback.service.BoardService;
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
//    @GetMapping("board/update/{no}")
//    public String boardReadList(@PathVariable("no")Long no,Model model){
//        BoardWriteDto boardWriteDto = boardService.getPost(no);
//
//        model.addAttribute("boardList", boardWriteDto);
//        return "board/update";
//    }
    @GetMapping("/edit/{no}")
    public String edit(@PathVariable("no")Long no, Model model){
        BoardWriteDto boardWriteDto = boardService.getPost(no);

        model.addAttribute("boardList", boardWriteDto);
        return "boards/update";
    }
    @GetMapping("/writeDetail/{no}")
    public String writeDetail(@PathVariable("no")Long no, Model model){
        BoardWriteDto boardWriteDto = boardService.getPost(no);

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
    public String codeView(){
        return "boards/codeView";
    }
    @GetMapping("/{no}")
    public String commentList(@PathVariable("no")Long no, Model model){
        List<CommentWriteDto> commentWriteDtoList = commentService.searchPosts(no);
        model.addAttribute("commentList", commentWriteDtoList);
        return "boards/writeDetail" + no.toString();
    }
    // 해당 게시글에 대해 댓글 쓰기
    @PostMapping("/{no}")
    public String commentWrite(@PathVariable("no")Long no,CommentWriteDto commentWriteDto){
        commentService.saveComment(commentWriteDto, no);
        return "redirect:/boards/writeDetail/" + no.toString();
    }
}
