package com.project.feedback.service;

import com.project.feedback.domain.dto.repository.RepositoryRequest;
import com.project.feedback.domain.entity.RepositoryEntity;
import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.exception.CustomException;
import com.project.feedback.exception.ErrorCode;
import com.project.feedback.repository.RepositoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RepositoryService {
    private final RepositoryRepository repositoryRepository;

    public Long save(RepositoryRequest request, UserEntity user) {
        return repositoryRepository.save(request.toEntity(user)).getId();
    }

    @Transactional
    public Long update(Long id, RepositoryRequest request, UserEntity user) {
        RepositoryEntity repository = repositoryRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.REPOSITORY_NOT_FOUND));

        // 소유권 확인
        if (!user.equals(repository.getUser())) throw new CustomException(ErrorCode.INVALID_PERMISSION);

        repository.update(request.toEntity());

        return repository.getId();
    }
}
