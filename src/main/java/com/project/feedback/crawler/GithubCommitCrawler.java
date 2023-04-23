package com.project.feedback.crawler;

import com.project.feedback.domain.entity.RepositoryEntity;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Slf4j
@Component
public class GithubCommitCrawler extends AbstractCommitCrawler {
    private static final String GITHUB_HOST = "https://github.com";
    private static final String URL_SELECTOR = ".Details > p > .markdown-title";
    private static final String COMMIT_SELECTOR = ".Details > p > .markdown-title";
    private static final String COMMITTED_DATETIME_SELECTOR = ".Details > div > div > relative-time";
    private static final String COMMIT_BUTTON_SELECTOR = ".Details.d-flex > div > ul > li > a";

    public GithubCommitCrawler() {
        super(URL_SELECTOR, COMMIT_SELECTOR, COMMITTED_DATETIME_SELECTOR);
    }

    @Override
    public Commit execute(RepositoryEntity repository) {
        try {
            Response response = Jsoup.connect(repository.getAddress())
                    .followRedirects(true)
                    .execute();
            Document html = response.parse();
            Elements commitButton = html.select(COMMIT_BUTTON_SELECTOR);
            if (!commitButton.isEmpty()) {
                String commitPath = commitButton.attr("href");
                repository.setAddress(GITHUB_HOST + commitPath);
            }
        } catch (IOException e) {
            log.error("[wrong url] user:{} {}", repository.getUser().getRealName(), e.getMessage());
            return Commit.wrong(repository);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return super.execute(repository);
    }

    @Override
    String getAddress(String address) {
        return address;
    }

    @Override
    String parseUrl(Elements urlElement) {
        if (urlElement.isEmpty()) return null;
        String url = urlElement.attr("href");
        return url.isBlank() ? null : GITHUB_HOST + url;
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
