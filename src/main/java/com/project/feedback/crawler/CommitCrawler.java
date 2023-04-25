package com.project.feedback.crawler;

import com.project.feedback.infra.outgoing.jpa.RepositoryEntity;

public interface CommitCrawler {
    Commit execute(RepositoryEntity repository);
}
