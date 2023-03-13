package com.project.feedback.service;

import com.project.feedback.crawler.Commit;
import com.project.feedback.crawler.CommitCrawler;
import com.project.feedback.domain.entity.RepositoryEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommitCrawlerService {
    private final CommitCrawler commitCrawler;
    private final Executor commitCrawlerExecutor;

    public List<Commit> crawl(List<RepositoryEntity> repositories) {
        List<CompletableFuture<Commit>> commitFuture = repositories.stream()
                .map(repository -> CompletableFuture.supplyAsync(() -> commitCrawler.execute(repository), commitCrawlerExecutor))
                .toList();

        return commitFuture.stream().map(CompletableFuture::join).toList();
    }
}
