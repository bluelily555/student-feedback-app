package com.project.feedback.service;

import com.project.feedback.domain.dto.comment.CommentCreateRequest;
import com.project.feedback.domain.dto.comment.CommentCreateResponse;
import com.project.feedback.domain.dto.comment.CommentListDto;
import com.project.feedback.domain.dto.comment.CommentListResponse;
import com.project.feedback.domain.entity.BoardEntity;
import com.project.feedback.domain.entity.CommentEntity;
import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.domain.enums.LikeContentType;
import com.project.feedback.repository.CommentRepository;
import com.project.feedback.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor

public class CommentService {

    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final FindService findService;

    public CommentCreateResponse saveComment(CommentCreateRequest req, String userName, Long boardId) {
        UserEntity loginUser = findService.findUserByUserName(userName);
        BoardEntity code = findService.findByBoardId(boardId);

        CommentEntity comment = commentRepository.save(req.toEntity(loginUser, code));

        return CommentCreateResponse.of(comment);
    }

    public CommentListResponse getCommentList(Long boardId, Pageable pageable){
        Page<CommentEntity> comments = commentRepository.findByBoardEntityId(boardId, pageable);

        List<CommentListDto> content = new ArrayList<>();
        for(CommentEntity comment : comments) {
            int likes = likeRepository.countByContentTypeAndContentIdAndStatusIsTrue(LikeContentType.COMMENT, comment.getId());
            content.add(CommentListDto.of(comment, likes));
        }

        return new CommentListResponse(content, pageable, comments);
    }





}

