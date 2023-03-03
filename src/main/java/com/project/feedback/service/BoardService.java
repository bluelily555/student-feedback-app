package com.project.feedback.service;

import com.project.feedback.domain.dto.board.BoardCreateRequest;
import com.project.feedback.domain.dto.board.BoardListDto;
import com.project.feedback.domain.entity.BoardEntity;
import com.project.feedback.domain.entity.TaskEntity;
import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.domain.enums.LikeContentType;
import com.project.feedback.repository.BoardRepository;
import com.project.feedback.repository.LikeRepository;
import com.project.feedback.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final LikeRepository likeRepository;
    private final TaskService taskService;

    private List<BoardListDto> getBoardWriteDtos(List<BoardEntity> boardEntities) {
        List<BoardListDto> codeWriteDtoList = new ArrayList<>();

        for(BoardEntity boardEntity : boardEntities){
            BoardListDto codeWriteDto = BoardListDto.builder()
                    .id(boardEntity.getId())
                    .content(boardEntity.getContent())
                    .codeContent(boardEntity.getCodeContent())
                    .title(boardEntity.getTitle())
                    .userName(boardEntity.getUser().getRealName())
                    .createdDate(boardEntity.getCreatedDate())
                    .build();
            codeWriteDtoList.add(codeWriteDto);
        }
        return codeWriteDtoList;
    }

    @Transactional
    public Long save(BoardCreateRequest boardCreateRequest, UserEntity user, TaskEntity taskEntity){

        BoardEntity boardEntity = boardCreateRequest.toEntity(user, taskEntity);
        BoardEntity savedBoardEntity = boardRepository.save(boardEntity);
        return savedBoardEntity.getId();
    }

    @Transactional
    public List<BoardListDto> searchAllCode(){
        List<BoardEntity> codeEntities = boardRepository.findAll();
        return getBoardWriteDtos(codeEntities);
    }

    @Transactional
    public List<BoardListDto> getBoardListByTaskId(Long taskId){
        List<BoardListDto> codeEntities = taskService.getOneTask(taskId).getBoards();
        return codeEntities;
    }

    @Transactional
    public List<BoardListDto> getBoardListByUserId(Long userId){
        List<BoardEntity> codeEntities = boardRepository.findAllByUserId(userId);
        return getBoardWriteDtos(codeEntities);
    }

    @Transactional
    public void delete(Long boardId){
        boardRepository.deleteById(boardId);
    }

    @Transactional
    public BoardListDto getBoardDetail(Long boardId){
        Optional<BoardEntity> boardEntityWrapper = boardRepository.findById(boardId);
        BoardEntity boardEntity = boardEntityWrapper.get();
        BoardListDto boardList = BoardListDto.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .codeContent(boardEntity.getCodeContent())
                .userName(boardEntity.getUser().getRealName())
                .likes(likeRepository.countByContentTypeAndContentIdAndStatusIsTrue(LikeContentType.BOARD, boardId))
                .createdDate(boardEntity.getCreatedDate())
                .build();
        return boardList;
    }
}
