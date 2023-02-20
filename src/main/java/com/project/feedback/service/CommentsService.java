package com.project.feedback.service;

import com.project.feedback.domain.dto.comment.CommentCreateRequest;
import com.project.feedback.domain.dto.comment.CommentCreateResponse;
import com.project.feedback.domain.dto.comment.CommentListDto;
import com.project.feedback.domain.dto.comment.CommentListResponse;
import com.project.feedback.domain.entity.CodeEntity;
import com.project.feedback.domain.entity.CommentsEntity;
import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.repository.CommentsRepository;
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
public class CommentsService {

    private final CommentsRepository commentRepository;
    private final FindService findService;

    public CommentCreateResponse saveComment(CommentCreateRequest req, String userName, Long codeId) {
        UserEntity loginUser = findService.findUserByUserName(userName);
        CodeEntity code = findService.findByCodeId(codeId);

        CommentsEntity comment = commentRepository.save(req.toEntity(loginUser, code));

        return CommentCreateResponse.of(comment);
    }

    public CommentListResponse getCommentList(Long codeId, Pageable pageable){
        Page<CommentsEntity> comments = commentRepository.findByCodeEntityId(codeId, pageable);

        List<CommentListDto> content = new ArrayList<>();
        for(CommentsEntity comment : comments) {
            content.add(CommentListDto.of(comment));
        }

        return new CommentListResponse(content, pageable, comments);
    }





}

