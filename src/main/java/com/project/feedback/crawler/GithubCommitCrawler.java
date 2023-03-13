package com.project.feedback.crawler;

import com.project.feedback.domain.entity.RepositoryEntity;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Slf4j
@Component
public class GithubCommitCrawler extends AbstractCommitCrawler {
    private static final String URL_SELECTOR = ".Details > p > .markdown-title";
    private static final String COMMIT_SELECTOR = ".Details > p > .markdown-title";
    private static final String COMMITTED_DATETIME_SELECTOR = ".Details > div > div > relative-time";
    private static final String COMMIT_URL_SUFFIX = "/commits/main";

    public GithubCommitCrawler() {
        super(URL_SELECTOR, COMMIT_SELECTOR, COMMITTED_DATETIME_SELECTOR);
    }

    @Override
    public Commit execute(RepositoryEntity repository) {
        repository.setAddress(repository.getAddress() + COMMIT_URL_SUFFIX);
        return super.execute(repository);
    }

    @Override
    String getAddress(String address) {
        return address.substring(0, address.length() - COMMIT_URL_SUFFIX.length());
    }

    @Override
    String parseUrl(Elements urlElement) {
        if (urlElement.isEmpty()) return null;
        String url = urlElement.attr("href");
        return url.isBlank() ? null : "https://github.com/" + url;
    }

    @Override
    String parseTitle(Elements commitElement) {
        if (commitElement.isEmpty()) return null;
        String title = commitElement.first().text();
        return title.isBlank() ? null : title;
    }

    @Override
    LocalDateTime parseCommittedDatetime(Elements committedDatetimeElement) {
        if (committedDatetimeElement.isEmpty()) return null;
        String datetime = committedDatetimeElement.first().attr("datetime");
        LocalDateTime localDateTime = null;
        try {
            localDateTime = LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
            localDateTime = localDateTime.plusHours(9); // UTC에서 KST로 변환.
        } catch (DateTimeParseException e) {
            log.error("{}", e.getMessage());
        }
        return localDateTime;
    }
}
