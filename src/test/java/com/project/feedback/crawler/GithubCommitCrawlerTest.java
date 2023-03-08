package com.project.feedback.crawler;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GithubCommitCrawlerTest {
    private final GithubCommitCrawler githubCommitCrawler = new GithubCommitCrawler();

    @Test
    void execute() {
        String algorithm = "https://github.com/Kyeongrok/java-algorithm";
        String javaBook = "https://github.com/Kyeongrok/java-book";
        List<String> addresses = new ArrayList<>(Arrays.asList(algorithm, javaBook));
        List<Commit> commits = assertDoesNotThrow(() -> githubCommitCrawler.execute(addresses));
        for (Commit commit : commits) {
            assertTrue(algorithm.equals(commit.getAddress()) || javaBook.equals(commit.getAddress()));
        }
    }

    @Test
    void execute_not_found_url() {
        String wrongURL = "https://github.com/Kyeongrok/java-algorithm999";
        List<String> addresses = new ArrayList<>(Arrays.asList(wrongURL));

        List<Commit> commits = githubCommitCrawler.execute(addresses);

        assertEquals(wrongURL, commits.get(0).getAddress());
    }

    @Test
    void execute_git_url() {
        String gitCloneURL= "https://github.com/Kyeongrok/java-algorithm.git";

        List<String> addresses = new ArrayList<>(Arrays.asList(gitCloneURL));

        List<Commit> commits = githubCommitCrawler.execute(addresses);

        assertEquals(gitCloneURL, commits.get(0).getAddress());
    }
}