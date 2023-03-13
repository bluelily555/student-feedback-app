package com.project.feedback.crawler;

import com.project.feedback.domain.entity.RepositoryEntity;
import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.fixture.RepositoryFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GithubCommitCrawlerTest {
    private final GithubCommitCrawler githubCommitCrawler = new GithubCommitCrawler();

    @Test
    void execute() {
        UserEntity dummy = UserEntity.builder().build();
        String algorithm = "https://github.com/Kyeongrok/java-algorithm";
        RepositoryEntity repository = RepositoryFixture.repository(algorithm, dummy);
        Commit commit = assertDoesNotThrow(() -> githubCommitCrawler.execute(repository));
        assertEquals(algorithm, commit.getAddress());
    }

    @Test
    void execute_not_found_url() {
        UserEntity dummy = UserEntity.builder().build();
        String wrongURL = "https://github.com/Kyeongrok/java-algorithm999";
        RepositoryEntity repository = RepositoryFixture.repository(wrongURL, dummy);

        Commit commit = githubCommitCrawler.execute(repository);

        assertEquals(wrongURL, commit.getAddress());
        assertNull(commit.getUrl());
        assertNull(commit.getCommittedDatetime());
        assertNull(commit.getTitle());
    }

    @Test
    void execute_git_url() {
        UserEntity dummy = UserEntity.builder().build();
        String gitCloneURL = "https://github.com/Kyeongrok/java-algorithm.git";
        RepositoryEntity repository = RepositoryFixture.repository(gitCloneURL, dummy);

        Commit commit = githubCommitCrawler.execute(repository);

        assertEquals(gitCloneURL, commit.getAddress());
    }
}