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
    // dto list를 리턴하는 함수
    private List<BoardWriteDto> getBoardWriteDtos(List<BoardEntity> boardEntities) {
        List<BoardWriteDto> boardWriteDtoList = new ArrayList<>();

        for(BoardEntity boardEntity : boardEntities){
            BoardWriteDto boardWriteDto = BoardWriteDto.builder()
                    .id(boardEntity.getId())
                    .title(boardEntity.getTitle())
                    .content(boardEntity.getContent())
                    .writer(boardEntity.getWriter())
                    .userName(boardEntity.getUserName())
                    .createdDate(boardEntity.getCreatedDate())
                    .build();

            boardWriteDtoList.add(boardWriteDto);
        }
        return boardWriteDtoList;
    }

    @Transactional
    public Long savePost(BoardWriteDto boardWriteDto){
        return boardRepository.save(boardWriteDto.toEntity()).getId();
    }

    @Transactional
    public List<BoardWriteDto> getBoardList(){
        List<BoardEntity> boardEntities = boardRepository.findAll();
        return getBoardWriteDtos(boardEntities);
    }
    @Transactional
    public List<BoardWriteDto> getBoardListByUserName(String userName){
        List<BoardEntity> boardEntities = boardRepository.findAllByUserName(userName);
        return getBoardWriteDtos(boardEntities);

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
                .userName(boardEntity.getUserName())
                .createdDate(boardEntity.getCreatedDate())
                .build();
        return boardWriteDto;
    }
    @Transactional
    public void deletePost(Long id){
        boardRepository.deleteById(id);
    }

}
