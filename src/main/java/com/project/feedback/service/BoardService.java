package com.project.feedback.service;

import com.project.feedback.domain.dto.board.BoardWriteDto;
import com.project.feedback.domain.entity.BoardEntity;
import com.project.feedback.repository.BoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BoardService {
    private BoardRepository boardRepository;

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
                .createdDate(boardEntity.getCreatedDate())
                .build();
        return boardWriteDto;
    }
    @Transactional
    public void deletePost(Long id){
        boardRepository.deleteById(id);
    }

}
