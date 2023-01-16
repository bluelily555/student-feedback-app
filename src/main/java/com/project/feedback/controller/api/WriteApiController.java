package com.project.feedback.controller.api;

import com.project.feedback.domain.Response;
import com.project.feedback.domain.dto.board.BoardWriteDto;
import com.project.feedback.domain.dto.board.CommentWriteDto;
import com.project.feedback.service.BoardService;
import com.project.feedback.service.CommentService;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "글 목록 조회", notes = "글 전체 조회")
    @GetMapping("/list")
    public Object writeList(Model model){
//        List<BoardWriteDto> boardWriteDtoList = boardService.getBoardList();
        List<BoardWriteDto> boardWriteDtoList = boardService.getBoardList();

        model.addAttribute("boardList", boardWriteDtoList);
        return boardWriteDtoList;
    }
    @ApiOperation(value= "글 하나 조회", notes = "글 하나만 조회")
    @GetMapping("/list/{no}")
    public Object writeListOne(@PathVariable("no")Long no, Model model){
        BoardWriteDto boardWriteDto = boardService.getPost(no);
        return boardWriteDto;
    }
    @ApiOperation(value="글 쓰기", notes = "writing manually")
    @PostMapping("/post")
    public Object writePost(BoardWriteDto boardWriteDto){
        boardService.savePost(boardWriteDto);
        return Response.success(boardWriteDto);
    }

    @ApiOperation(value="글 삭제", notes = "글 삭제")
    @DeleteMapping("/delete/{no}")
    public Object writePut(@PathVariable("no")Long no){
        boardService.deletePost(no);
        return Response.success(no);
    }
    @ApiOperation(value="글 수정", notes = "글 수정")
    @PutMapping("/put/{id}")
    public Object writePut(@PathVariable("id")Long id, BoardWriteDto boardWriteDto){
        boardWriteDto.setId(id);
        boardService.savePost(boardWriteDto);
        return Response.success(boardWriteDto);
    }
    @ApiOperation(value="댓글 작성", notes="해당 글에 댓글 작성")
    @PostMapping("/{postId}/comments")
    public Object commentPost(CommentWriteDto commentWriteDto, @PathVariable("postId")Long no){
        commentService.savePost(commentWriteDto, no);
        return Response.success(commentWriteDto);
    }
}
