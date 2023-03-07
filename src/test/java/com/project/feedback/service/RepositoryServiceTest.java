package com.project.feedback.service;

import com.project.feedback.domain.entity.RepositoryEntity;
import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.exception.CustomException;
import com.project.feedback.exception.ErrorCode;
import com.project.feedback.fixture.RepositoryFixture;
import com.project.feedback.repository.RepositoryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

    @Test
    void update() {
        // given
        UserEntity dummy = UserEntity.builder().build();
        given(repositoryRepository.findById(anyLong())).willReturn(Optional.of(RepositoryFixture.securityRepo(dummy)));

        // when
        Long result = assertDoesNotThrow(() -> repositoryService.update(1L, RepositoryFixture.snsRepoRequest(), dummy));

        // then
        assertEquals(1L, result);
        verify(repositoryRepository).findById(anyLong());
    }

    @Test
    void update_not_found_repository() {
        // given
        UserEntity dummy = UserEntity.builder().build();
        given(repositoryRepository.findById(anyLong())).willReturn(Optional.empty());

        // when
        // then
        CustomException e = assertThrows(CustomException.class, () -> repositoryService.update(1L, RepositoryFixture.snsRepoRequest(), dummy));
        assertEquals(ErrorCode.REPOSITORY_NOT_FOUND, e.getErrorCode());
    }

    @Test
    void update_invalid_permission() {
        // given
        UserEntity owner = UserEntity.builder().build();
        UserEntity requester = UserEntity.builder().build();
        given(repositoryRepository.findById(anyLong())).willReturn(Optional.of(RepositoryFixture.securityRepo(owner)));

        // when
        // then
        CustomException e = assertThrows(CustomException.class, () -> repositoryService.update(1L, RepositoryFixture.snsRepoRequest(), requester));
        assertEquals(ErrorCode.INVALID_PERMISSION, e.getErrorCode());
    }
}