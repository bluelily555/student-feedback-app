package com.project.feedback.service;

import com.project.feedback.domain.dto.repository.RepositoryRequest;
import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.repository.RepositoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RepositoryService {
    private final RepositoryRepository repositoryRepository;

    public Long save(RepositoryRequest request, UserEntity user) {
        return repositoryRepository.save(request.toEntity(user)).getId();
    }
}
