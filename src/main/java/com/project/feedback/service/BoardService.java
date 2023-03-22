package com.project.feedback.service;

import com.project.feedback.domain.dto.board.BoardCreateRequest;
import com.project.feedback.domain.dto.board.BoardListDto;
import com.project.feedback.domain.dto.board.BoardListResponse;
import com.project.feedback.domain.entity.BoardEntity;
import com.project.feedback.domain.entity.ImageEntity;
import com.project.feedback.domain.entity.TaskEntity;
import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.domain.enums.LikeContentType;
import com.project.feedback.repository.BoardRepository;
import com.project.feedback.repository.LikeRepository;
import com.project.feedback.upload.ImageManager;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final LikeRepository likeRepository;
    private final TaskService taskService;
    private final ImageManager imageManager;

    private List<BoardListDto> getBoardWriteDtos(List<BoardEntity> boardEntities) {
        List<BoardListDto> codeWriteDtoList = new ArrayList<>();

        for(BoardEntity boardEntity : boardEntities){
            BoardListDto codeWriteDto = BoardListDto.builder()
                    .id(boardEntity.getId())
                    .taskEntity(boardEntity.getTaskEntity())
                    .content(boardEntity.getContent())
                    .codeContent(boardEntity.getCodeContent())
                    .language(boardEntity.getLanguage())
                    .title(boardEntity.getTitle())
                    .userName(boardEntity.getUser().getRealName())
                    .createdDate(boardEntity.getCreatedDate())
                    .build();
            codeWriteDtoList.add(codeWriteDto);
        }
        return codeWriteDtoList;
    }
    private List<BoardListDto> getBoardWriteDtos(Page<BoardEntity> boardEntities) {
        // 함수 오버로딩
        List<BoardListDto> codeWriteDtoList = new ArrayList<>();

        for(BoardEntity boardEntity : boardEntities){
            BoardListDto codeWriteDto = BoardListDto.builder()
                    .id(boardEntity.getId())
                    .taskEntity(boardEntity.getTaskEntity())
                    .content(boardEntity.getContent())
                    .codeContent(boardEntity.getCodeContent())
                    .language(boardEntity.getLanguage())
                    .title(boardEntity.getTitle())
                    .userName(boardEntity.getUser().getRealName())
                    .createdDate(boardEntity.getCreatedDate())
                    .build();
            codeWriteDtoList.add(codeWriteDto);
        }
        return codeWriteDtoList;
    }

    @Transactional
    public Long save(BoardCreateRequest boardCreateRequest, MultipartFile file, UserEntity user, TaskEntity taskEntity) {

        BoardEntity boardEntity = boardCreateRequest.toEntity(user, taskEntity);

        // 이미지 저장
        if (file != null && !file.isEmpty()) {
            String fileName = imageManager.upload(file);
            ImageEntity image = ImageEntity.of(fileName);
            boardEntity.addImage(image);
        }

        BoardEntity savedBoardEntity = boardRepository.save(boardEntity);
        return savedBoardEntity.getId();
    }
    @Transactional
    public BoardListResponse searchByTitle(Pageable pageable, String title){
        Page<BoardEntity> boards = boardRepository.findByTitleContaining(pageable, title);
        List<BoardListDto> boardListDtoList = getBoardWriteDtos(boards);
        return new BoardListResponse(boardListDtoList, pageable, boards);
    }
    @Transactional
    public Long save(BoardCreateRequest boardCreateRequest, UserEntity user, TaskEntity taskEntity){
        return save(boardCreateRequest, null, user, taskEntity);
    }

    @Transactional
    public BoardListResponse searchAllCode(Pageable pageable){
        // pageable 로 찾은 board 데이터
        Page<BoardEntity> boards = boardRepository.findAll(pageable);
        // id 기준으로 내림차순
//        List<BoardEntity> boardEntities = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<BoardListDto> boardListDtoList = getBoardWriteDtos(boards);

        return new BoardListResponse(boardListDtoList, pageable, boards);
    }

    @Transactional
    public List<BoardListDto> getBoardListByTaskId(Pageable pageable, Long taskId){
        Page<BoardEntity> boards = boardRepository.findByTaskEntityId(pageable, taskId);
        List<BoardListDto> boardListDtoList = getBoardWriteDtos(boards);
        return boardListDtoList;
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
                .taskEntity(boardEntity.getTaskEntity())
                .language(boardEntity.getLanguage())
                .content(boardEntity.getContent())
                .codeContent(boardEntity.getCodeContent())
                .userName(boardEntity.getUser().getRealName())
                .likes(likeRepository.countByContentTypeAndContentIdAndStatusIsTrue(LikeContentType.BOARD, boardId))
                .images(boardEntity.getImages().stream().map(ImageEntity::getName).toList())
                .createdDate(boardEntity.getCreatedDate())
                .build();
        return boardList;
    }
}
