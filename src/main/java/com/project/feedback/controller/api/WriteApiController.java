package com.project.feedback.controller.api;

import com.project.feedback.domain.Response;
import com.project.feedback.domain.dto.board.BoardWriteDto;
import com.project.feedback.domain.dto.board.CommentWriteDto;
import com.project.feedback.service.BoardService;
import com.project.feedback.service.CommentService;
import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/boards")
public class WriteApiController {
    private BoardService boardService;
    private CommentService commentService;

    @GetMapping("/list")
    public Object writeList(Model model){
//        List<BoardWriteDto> boardWriteDtoList = boardService.getBoardList();
        List<BoardWriteDto> boardWriteDtoList = boardService.getBoardList();

        model.addAttribute("boardList", boardWriteDtoList);
        return boardWriteDtoList;
    }
    @GetMapping("/list/{no}")
    public Object writeListOne(@PathVariable("no")Long no, Model model){
        BoardWriteDto boardWriteDto = boardService.getPost(no);
        return boardWriteDto;
    }
    @PostMapping("/post")
    public Object writePost(BoardWriteDto boardWriteDto){
        boardService.savePost(boardWriteDto);
        return Response.success(boardWriteDto);
    }

    @DeleteMapping("/delete/{no}")
    public Object writePut(@PathVariable("no")Long no){
        boardService.deletePost(no);
        return Response.success(no);
    }
    @PutMapping("/put/{id}")
    public Object writePut(@PathVariable("id")Long id, BoardWriteDto boardWriteDto){
        boardWriteDto.setId(id);
        boardService.savePost(boardWriteDto);
        return Response.success(boardWriteDto);
    }
    @PostMapping("/{postId}/comments")
    public Object commentPost(CommentWriteDto commentWriteDto, @PathVariable("postId")Long no){
        commentService.saveComment(commentWriteDto, no);
        return Response.success(commentWriteDto);
    }
}
