package com.project.feedback.fixture;

import com.project.feedback.domain.dto.repository.RepositoryRequest;
import com.project.feedback.domain.entity.RepositoryEntity;
import com.project.feedback.domain.entity.UserEntity;

public class RepositoryFixture {
    public static RepositoryEntity securityRepo(UserEntity user) {
        return RepositoryEntity.builder()
                .id(1L)
                .name("security")
                .address("https://www.github.com/username/repo")
                .user(user)
                .build();
    }

    public static RepositoryRequest securityRepoRequest() {
        return RepositoryRequest.builder()
                .name("security")
                .address("https://www.github.com/username/repo")
                .build();
    }
}
