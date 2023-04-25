package com.project.feedback.crawler;

import com.project.feedback.infra.outgoing.jpa.RepositoryEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class Commit {
    private static int DEFAULT_OFFSET_MINUTES = 30;
    private String username;
    private String address;
    private String url;
    private String title;
    private LocalDateTime committedDatetime;

    private Commit() {
    }

    public static Commit empty() {
        return new Commit();
    }

    public static Commit wrong(RepositoryEntity repository) {
        Commit commit = Commit.empty();
        commit.setUsername(repository.getName());
        commit.setAddress(repository.getAddress());
        return commit;
    }

    public String getCommittedDatetime() {
        if (committedDatetime == null) return null;
        return committedDatetime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public boolean isNew() {
        return committedDatetime.isAfter(LocalDateTime.now().minusMinutes(DEFAULT_OFFSET_MINUTES));
    }

    @Override
    public String toString() {
        return "Commit{" +
                "username='" + username + '\'' +
                ", address='" + address + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", committedDatetime=" + committedDatetime +
                '}';
    }
}
