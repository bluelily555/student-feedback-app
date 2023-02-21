package com.project.feedback.service;

import com.project.feedback.domain.dto.board.BoardCreateRequest;
import com.project.feedback.domain.dto.board.BoardWriteDto;
import com.project.feedback.domain.entity.BoardEntity;
import com.project.feedback.domain.entity.TaskEntity;
import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.repository.BoardRepository;
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
    private final TaskRepository taskRepository;
    private final TaskService taskService;

    private List<BoardWriteDto> getCodeWriteDtos(List<BoardEntity> boardEntities) {
        List<BoardWriteDto> codeWriteDtoList = new ArrayList<>();

        for(BoardEntity boardEntity : boardEntities){
            BoardWriteDto codeWriteDto = BoardWriteDto.builder()
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
    public Long saveCode(BoardCreateRequest boardCreateRequest, UserEntity user, TaskEntity taskEntity){

        BoardEntity boardEntity = boardCreateRequest.toEntity(user, taskEntity);
        BoardEntity savedBoardEntity = boardRepository.save(boardEntity);
        return savedBoardEntity.getId();
    }

    @Transactional
    public List<BoardWriteDto> searchAllCode(){
        List<BoardEntity> codeEntities = boardRepository.findAll();
        return getCodeWriteDtos(codeEntities);
    }

    @Transactional
    public List<BoardWriteDto> getCodeListByTaskId(Long taskId){
       // List<CodeEntity> codeEntities = boardRepository.findAllByTaskId(taskId);
        List<BoardWriteDto> codeEntities = taskService.getOneTask(taskId).getBoards();
        return codeEntities;
    }

    @Transactional
    public List<BoardWriteDto> getCodeListByUserId(Long userId){
        List<BoardEntity> codeEntities = boardRepository.findAllByUserId(userId);
        return getCodeWriteDtos(codeEntities);
    }

    @Transactional
    public void deleteCode(Long boardId){
        boardRepository.deleteById(boardId);
    }
    @Transactional
    public BoardWriteDto getCodeDetail(Long boardId){
        Optional<BoardEntity> codeEntityWrapper = boardRepository.findById(boardId);
        BoardEntity boardEntity = codeEntityWrapper.get();
        BoardWriteDto codeWriteDto = BoardWriteDto.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .codeContent(boardEntity.getCodeContent())
                .userName(boardEntity.getUser().getRealName())
                .createdDate(boardEntity.getCreatedDate())
                .build();
        return codeWriteDto;
    }
}
