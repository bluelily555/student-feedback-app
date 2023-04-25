package com.project.feedback.crawler;

import com.project.feedback.infra.outgoing.entity.RepositoryEntity;
import com.project.feedback.infra.outgoing.entity.UserEntity;
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
        assertTrue(commit.getAddress().startsWith(algorithm + "/commits"));
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
    void execute_tree_url() {
        UserEntity dummy = UserEntity.builder().build();
        String treeURL = "https://github.com/Kyeongrok/java-algorithm/tree/main/src";
        String commitURL = "https://github.com/Kyeongrok/java-algorithm/commits/main/src";
        RepositoryEntity repository = RepositoryFixture.repository(treeURL, dummy);

        Commit commit = githubCommitCrawler.execute(repository);
        assertEquals(commitURL, commit.getAddress());
    }

    @Test
    void execute_git_url() {
        UserEntity dummy = UserEntity.builder().build();
        String algorithm = "https://github.com/Kyeongrok/java-algorithm";
        String algorithmGit = "https://github.com/Kyeongrok/java-algorithm.git";
        RepositoryEntity repository = RepositoryFixture.repository(algorithmGit, dummy);
        Commit commit = assertDoesNotThrow(() -> githubCommitCrawler.execute(repository));
        assertTrue(commit.getAddress().startsWith(algorithm + "/commits"));
    }
}