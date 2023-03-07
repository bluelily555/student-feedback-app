package com.project.feedback.fixture;

import com.project.feedback.domain.dto.repository.RepositoryRequest;
import com.project.feedback.domain.entity.RepositoryEntity;
import com.project.feedback.domain.entity.UserEntity;

public class RepositoryFixture {
    public static RepositoryEntity securityRepo(UserEntity user) {
        return RepositoryEntity.builder()
                .id(1L)
                .name("security")
                .address("https://www.github.com/username/security")
                .user(user)
                .build();
    }

    public static RepositoryEntity snsRepo(UserEntity user) {
        return RepositoryEntity.builder()
                .id(1L)
                .name("sns")
                .address("https://www.github.com/username/sns")
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
