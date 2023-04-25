package com.project.feedback.crawler;

import com.project.feedback.infra.outgoing.entity.RepositoryEntity;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDateTime;

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
    public Commit execute(RepositoryEntity repository) {
        Commit commit = Commit.empty();
        commit.setUsername(repository.getUser().getRealName());
        commit.setAddress(getAddress(repository.getAddress()));

        try {
            log.info("크롤링 시작");
            Connection conn = Jsoup.connect(repository.getAddress());
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
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            log.info("크롤링 종료");
        }
        return commit;
    }


    abstract String getAddress(String address);

    abstract String parseUrl(Elements urlElement);

    abstract String parseTitle(Elements commitElement);

    abstract LocalDateTime parseCommittedDatetime(Elements committedDatetimeElement);
}
