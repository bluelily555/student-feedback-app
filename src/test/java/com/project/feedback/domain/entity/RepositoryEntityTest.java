package com.project.feedback.domain.entity;

import com.project.feedback.fixture.RepositoryFixture;
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
}