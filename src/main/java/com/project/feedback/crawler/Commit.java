package com.project.feedback.crawler;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Commit {
    private String address;
    private String url;
    private String title;
    private LocalDateTime committedDatetime;

    private Commit() {
    }

    public static Commit empty() {
        return new Commit();
    }

    @Override
    public String toString() {
        return "Commit{" +
                "address='" + address + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", committedDatetime=" + committedDatetime +
                '}';
    }
}
