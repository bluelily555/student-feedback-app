package com.project.feedback.config;

import com.project.feedback.crawler.CommitCrawler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.Executor;

@Configuration
@Slf4j
@AllArgsConstructor
public class ScheduleConfig {
    private final CommitCrawler commitCrawler;
    private final Executor commitCrawlerExecutor;

    @Scheduled(cron = "*/10 * * * * *")
    public void run() {

        log.info("hello2----");
    }
}
