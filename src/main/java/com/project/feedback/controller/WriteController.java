package com.project.feedback.controller;

import com.project.feedback.domain.Response;
import com.project.feedback.domain.dto.board.BoardWriteDto;
import com.project.feedback.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class WriteController {

    private BoardService boardService;


    @GetMapping("board/write")
    public String boardReadWrite(){
        return "board/write";
    }
    @GetMapping("board/list")
    public String boardWriteList(Model model){
        List<BoardWriteDto> boardWriteDtoList = boardService.getBoardList();

        model.addAttribute("boardList", boardWriteDtoList);
        System.out.println(boardWriteDtoList);
        return "board/list";
    }
    @PostMapping("board/write")
    public String boardAddWrite(BoardWriteDto boardWriteDto){
        boardService.savePost(boardWriteDto);
        return "redirect:/board/list";
    }
    @GetMapping("post/{no}")
    public String detail(@PathVariable("no")Long no, Model model){
        BoardWriteDto boardWriteDto = boardService.getPost(no);

        model.addAttribute("boardList", boardWriteDto);
        return "board/list";
    }
//    @GetMapping("board/update/{no}")
//    public String boardReadList(@PathVariable("no")Long no,Model model){
//        BoardWriteDto boardWriteDto = boardService.getPost(no);
//
//        model.addAttribute("boardList", boardWriteDto);
//        return "board/update";
//    }
    @GetMapping("post/edit/{no}")
    public String edit(@PathVariable("no")Long no, Model model){
        BoardWriteDto boardWriteDto = boardService.getPost(no);

        model.addAttribute("boardList", boardWriteDto);
        return "board/update";
    }
    @PutMapping("post/edit/{no}")
    public String update(BoardWriteDto boardWriteDto, @PathVariable("no")Long no){
        boardService.savePost(boardWriteDto);

        return "redirect:/board/list";
    }
    @DeleteMapping("post/{no}")
    public String delete(@PathVariable("no")Long no){
        boardService.deletePost(no);

        return "redirect:/board/list";
    }
    @GetMapping("/board/codeView")
    public String codeView(){
        return "board/codeView";
    }
}
