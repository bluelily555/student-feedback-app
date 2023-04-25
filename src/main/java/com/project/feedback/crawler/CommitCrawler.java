package com.project.feedback.crawler;

import com.project.feedback.infra.outgoing.entity.RepositoryEntity;

public interface CommitCrawler {
    Commit execute(RepositoryEntity repository);
}
