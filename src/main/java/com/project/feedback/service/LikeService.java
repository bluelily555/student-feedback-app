package com.project.feedback.service;

import com.project.feedback.domain.entity.LikeEntity;
import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.domain.enums.LikeContentType;
import com.project.feedback.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;

    @Transactional
    public Long like(LikeContentType type, Long contentId, UserEntity from) {
        LikeEntity likeEntity = likeRepository.findByContentTypeAndContentIdAndFromUser(type, contentId, from)
                .orElseGet(() -> initializeAndSave(type, contentId, from));

        likeEntity.like();

        return likeEntity.getId();
    }

    @Transactional
    public Long unlike(LikeContentType type, Long contentId, UserEntity from) {
        Optional<LikeEntity> likeEntityOptional = likeRepository.findByContentTypeAndContentIdAndFromUser(type, contentId, from);

        if (likeEntityOptional.isEmpty()) return null;

        LikeEntity likeEntity = likeEntityOptional.get();

        likeEntity.unlike();

        return likeEntity.getId();
    }

    public boolean verifyLikeStatusOfBoard(Long boardId, UserEntity from) {
        return verifyLikeStatus(LikeContentType.BOARD, boardId, from);
    }

    public boolean verifyLikeStatusOfComment(Long commentId, UserEntity from) {
        return verifyLikeStatus(LikeContentType.COMMENT, commentId, from);
    }

    private boolean verifyLikeStatus(LikeContentType type, Long contentId, UserEntity from) {
        Optional<LikeEntity> likeEntityOptional = likeRepository.findByContentTypeAndContentIdAndFromUser(type, contentId, from);

        if (likeEntityOptional.isEmpty()) return false;

        return likeEntityOptional.get().isStatus();
    }

    public int countLikesOfBoard(Long boardId) {
        return countLikes(LikeContentType.BOARD, boardId);
    }

    private int countLikes(LikeContentType type, Long contentId) {
        return likeRepository.countByContentTypeAndContentIdAndStatusIsTrue(type, contentId);
    }

    private LikeEntity initializeAndSave(LikeContentType type, Long contentId, UserEntity from) {
        return likeRepository.save(LikeEntity.of(type, contentId, from));
    }
}
