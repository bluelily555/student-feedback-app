package com.project.feedback.application;

import com.project.feedback.domain.dto.board.BoardCreateRequest;
import com.project.feedback.domain.dto.board.BoardListDto;
import com.project.feedback.domain.dto.board.BoardListResponse;
import com.project.feedback.domain.dto.board.BoardModifyRequest;
import com.project.feedback.domain.enums.LikeContentType;
import com.project.feedback.domain.enums.NotificationType;
import com.project.feedback.exception.CustomException;
import com.project.feedback.exception.ErrorCode;
import com.project.feedback.infra.outgoing.entity.*;
import com.project.feedback.infra.outgoing.repository.TaskRepository;
import com.project.feedback.repository.*;
import com.project.feedback.upload.BoardImageManager;
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
    private final TaskRepository taskRepository;
    private final ImageRepository imageRepository;
    private final NotificationRepository notificationRepository;
    private final BoardImageManager boardImageManager;

    // 글 목록 불러오기
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
            String fileName = boardImageManager.upload(file);
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
    public BoardListResponse getBoardListByUserId(Long userId, Pageable pageable){
        Page<BoardEntity> boardEntities = boardRepository.findAllByUserId(pageable, userId);
        List<BoardListDto> boards = getBoardWriteDtos(boardEntities);
        return new BoardListResponse(boards, pageable, boardEntities);
    }


    public List<Long> getBoardCountByUserId(Long userId){
        List<Long> result = new ArrayList<>();
        List<BoardEntity> boards = boardRepository.findByUserId(userId);
        boards.forEach( b -> {
           result.add(b.getId());
        });
        return result;
    }

    @Transactional
    public BoardListDto modifyBoard(Long boardId, BoardModifyRequest request, MultipartFile file) {
        BoardEntity board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        TaskEntity task = taskRepository.getReferenceById(request.getTaskId());

        List<String> deleteImgList = request.getDeleteImageNames();

        // 이미지 삭제
        if (!deleteImgList.isEmpty()) {
            boardImageManager.deleteAll(deleteImgList);
            List<ImageEntity> imageEntities = imageRepository.findByNameIn(deleteImgList);
            imageRepository.deleteAll(imageEntities);
            board.deleteImageAll(imageEntities);
        }

        // 새로운 이미지 저장
        if (file != null && !file.isEmpty()) {
            String fileName = boardImageManager.upload(file);
            ImageEntity image = ImageEntity.of(fileName);
            board.addImage(image);
        }

        // board entity 수정
        BoardEntity modifyEntity = request.toEntity(task);

        board.modify(modifyEntity);

        return BoardListDto.detailOf(board);
    }

    @Transactional
    public void delete(Long boardId, UserEntity loginUser){
        BoardEntity board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        if(!board.equalsOwner(loginUser) && !loginUser.isManager()) throw new CustomException(ErrorCode.INVALID_PERMISSION);

        // 이미지 삭제
        board.getImages().forEach(image -> boardImageManager.delete(image.getName()));

        // 좋아요 삭제
        likeRepository.deleteByContentTypeAndContentId(LikeContentType.BOARD, boardId);

        // 댓글 좋아요 삭제
        for (CommentEntity comment : board.getComments()) {
            likeRepository.deleteByContentTypeAndContentId(LikeContentType.COMMENT, comment.getId());
        }

        // 관련 알림 삭제
        notificationRepository.deleteByTypeInAndSourceId(NotificationType.SOURCE_IS_BOARD, boardId);

        delete(boardId);
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
