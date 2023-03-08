package com.project.feedback.crawler;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
public abstract class AbstractCommitCrawler implements CommitCrawler {
    private final String urlSelector;
    private final String commitSelector;
    private final String committedDatetimeSelector;

    public AbstractCommitCrawler(String urlSelector, String commitSelector, String committedDatetimeSelector) {
        this.urlSelector = urlSelector;
        this.commitSelector = commitSelector;
        this.committedDatetimeSelector = committedDatetimeSelector;
    }

    @Override
    public List<Commit> execute(Collection<String> addresses) {
        CompletableFuture<Commit>[] processes = new CompletableFuture[addresses.size()];
        int index = 0;
        for (String address : addresses) {
            processes[index++] = asyncCrawl(address);
        }

        CompletableFuture.allOf(processes).join();

        List<Commit> result = new ArrayList<>();
        try {
            for (CompletableFuture<Commit> process : processes) {
                result.add(process.get());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return result;
    }

    @Async("commitCrawlerExecutor")
    CompletableFuture<Commit> asyncCrawl(String address) {
        Commit commit = Commit.empty();
        commit.setAddress(getAddress(address));

        try {
            log.info("크롤링 시작");
            Connection conn = Jsoup.connect(address);
            Document html = conn.get();

            // url 크롤링
            Elements urlElement = html.select(urlSelector);
            commit.setUrl(parseUrl(urlElement));

            // commit title 크롤링
            Elements commitElement = html.select(commitSelector);
            commit.setTitle(parseTitle(commitElement));

            // committedDatetime 크롤링
            Elements committedDatetimeElement = html.select(committedDatetimeSelector);
            commit.setCommittedDatetime(parseCommittedDatetime(committedDatetimeElement));
        } catch (IOException e) {
            log.error("Crawling URL : " + address, e);
        } finally {
            log.info("크롤링 종료");
        }
        return CompletableFuture.completedFuture(commit);
    }

    abstract String getAddress(String address);

    abstract String parseUrl(Elements urlElement);

    abstract String parseTitle(Elements commitElement);

    abstract LocalDateTime parseCommittedDatetime(Elements committedDatetimeElement);
}
