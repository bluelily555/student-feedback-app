package com.project.feedback.application;

import com.project.feedback.domain.dto.repository.RepositoryRequest;
import com.project.feedback.infra.outgoing.jpa.RepositoryEntity;
import com.project.feedback.infra.outgoing.jpa.UserEntity;
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
        RepositoryEntity repository = findById(id);

        // 소유권 확인
        verifyPermission(repository, user);

        repository.update(request.toEntity());

        return repository.getId();
    }

    @Transactional
    public Long delete(Long id, UserEntity user) {
        RepositoryEntity repository = findById(id);

        // 소유권 확인
        verifyPermission(repository, user);

        repositoryRepository.delete(repository);

        return repository.getId();
    }

    private RepositoryEntity findById(Long id) {
        return repositoryRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.REPOSITORY_NOT_FOUND));
    }

    private void verifyPermission(RepositoryEntity repository, UserEntity user) {
        if (!repository.equalsOwner(user)) throw new CustomException(ErrorCode.INVALID_PERMISSION);
    }
}
