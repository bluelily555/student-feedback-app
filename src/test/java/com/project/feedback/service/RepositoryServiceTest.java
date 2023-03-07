package com.project.feedback.service;

import com.project.feedback.domain.entity.RepositoryEntity;
import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.fixture.RepositoryFixture;
import com.project.feedback.repository.RepositoryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class RepositoryServiceTest {
    private final RepositoryRepository repositoryRepository = Mockito.mock(RepositoryRepository.class);
    private final RepositoryService repositoryService = new RepositoryService(repositoryRepository);

    @Test
    void save() {
        // given
        given(repositoryRepository.save(any(RepositoryEntity.class))).willReturn(RepositoryFixture.securityRepo(UserEntity.builder().build()));

        // when
        Long result = assertDoesNotThrow(() -> repositoryService.save(RepositoryFixture.securityRepoRequest(), UserEntity.builder().build()));

        // then
        assertEquals(1L, result);
        verify(repositoryRepository).save(any(RepositoryEntity.class));
    }
}