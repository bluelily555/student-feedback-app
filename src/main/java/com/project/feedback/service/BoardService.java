package com.project.feedback.service;

import com.project.feedback.domain.dto.board.BoardWriteDto;
import com.project.feedback.domain.entity.BoardEntity;
import com.project.feedback.repository.BoardRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Getter
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public Long savePost(BoardWriteDto boardWriteDto){
        return boardRepository.save(boardWriteDto.toEntity()).getId();
    }

    @Transactional
    public List<BoardWriteDto> getBoardList(){
        List<BoardEntity> boardEntities = boardRepository.findAll();
        List<BoardWriteDto> boardWriteDtoList = new ArrayList<>();

        for(BoardEntity boardEntity : boardEntities){
            BoardWriteDto boardWriteDto = BoardWriteDto.builder()
                    .id(boardEntity.getId())
                    .title(boardEntity.getTitle())
                    .content(boardEntity.getContent())
                    .writer(boardEntity.getWriter())
                    .userId(boardEntity.getUserId())
                    .createdDate(boardEntity.getCreatedDate())
                    .build();

            boardWriteDtoList.add(boardWriteDto);
        }
        return boardWriteDtoList;
    }
    @Transactional
    public BoardWriteDto getPost(Long id){
        Optional<BoardEntity> boardEntityWrapper = boardRepository.findById(id);
        BoardEntity boardEntity = boardEntityWrapper.get();

        BoardWriteDto boardWriteDto = BoardWriteDto.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .writer(boardEntity.getWriter())
                .userId(boardEntity.getUserId())
                .createdDate(boardEntity.getCreatedDate())
                .build();
        return boardWriteDto;
    }
    @Transactional
    public void deletePost(Long id){
        boardRepository.deleteById(id);
    }

}
