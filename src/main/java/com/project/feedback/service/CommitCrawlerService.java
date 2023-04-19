package com.project.feedback.service;

import com.project.feedback.crawler.Commit;
import com.project.feedback.crawler.CommitCrawler;
import com.project.feedback.domain.CrawlDate;
import com.project.feedback.domain.entity.RepositoryEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommitCrawlerService {
    private final CommitCrawler commitCrawler;
    private final Executor commitCrawlerExecutor;

    public static CrawlDate crawlDate = new CrawlDate(LocalDateTime.now());
    public static HashMap<String, List<Commit>> crawlResults = new HashMap<>();

    public List<Commit> crawl(String repositoryName, List<RepositoryEntity> repositories) {
        List<CompletableFuture<Commit>> commitFuture = repositories.stream()
                .map(repository -> CompletableFuture.supplyAsync(() -> commitCrawler.execute(repository), commitCrawlerExecutor))
                .toList();

        // 현재 시간 static맴버에 기록
        crawlDate.setLastCrawlDateTime(LocalDateTime.now());

        List<Commit> result = commitFuture.stream().map(CompletableFuture::join).toList();

        // map의 key에 값 추가
        if(crawlResults.containsKey(repositoryName)){
            crawlResults.remove(repositoryName);
            crawlResults.put(repositoryName, result);
        } else {
            crawlResults.put(repositoryName, result);
        }

        return result;
    }
}
