package com.project.feedback.domain;

import lombok.AllArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Setter
public class CrawlDate {
    LocalDateTime lastCrawlDateTime;

    public String getYYMMDDHHMMSS() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = this.lastCrawlDateTime.format(formatter);
        return formattedDateTime;
    }

}
