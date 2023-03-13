package com.project.feedback.domain.dto.commitCrawler;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommitCrawlerRequest {
    private String repositoryName;

    public boolean isEmpty() {
        return repositoryName == null || repositoryName.isBlank();
    }
}
