package com.project.feedback.domain.entity;

import com.project.feedback.fixture.RepositoryFixture;
import com.project.feedback.infra.outgoing.jpa.RepositoryEntity;
import com.project.feedback.infra.outgoing.jpa.UserEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryEntityTest {

    @Test
    void update() {
        UserEntity dummy = UserEntity.builder().build();
        RepositoryEntity base = RepositoryFixture.securityRepo(dummy);
        RepositoryEntity updated = RepositoryFixture.snsRepo(dummy);

        assertEquals("security", base.getName());
        assertEquals("https://www.github.com/username/security", base.getAddress());

        base.update(updated);

        assertEquals("sns", base.getName());
        assertEquals("https://www.github.com/username/sns", base.getAddress());
    }

    @Test
    void equalsOwner_true() {
        UserEntity owner = UserEntity.builder().build();
        RepositoryEntity repository = RepositoryFixture.securityRepo(owner);

        assertTrue(repository.equalsOwner(owner));
    }

    @Test
    void equalsOwner_false() {
        UserEntity owner = UserEntity.builder().build();
        UserEntity user = UserEntity.builder().build();
        RepositoryEntity repository = RepositoryFixture.securityRepo(owner);

        assertFalse(repository.equalsOwner(user));
    }
}