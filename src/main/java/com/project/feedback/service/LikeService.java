package com.project.feedback.service;

import com.project.feedback.domain.entity.LikeEntity;
import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.domain.enums.LikeContentType;
import com.project.feedback.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;

    @Transactional
    public Long like(LikeContentType type, Long contentId, UserEntity from) {
        LikeEntity likeEntity = likeRepository.findByTypeAndContentIdAndFromUser(type, contentId, from)
                .orElseGet(() -> initializeAndSave(type, contentId, from));

        likeEntity.like();

        return likeEntity.getId();
    }

    private LikeEntity initializeAndSave(LikeContentType type, Long contentId, UserEntity from) {
        return likeRepository.save(LikeEntity.of(type, contentId, from));
    }
}
