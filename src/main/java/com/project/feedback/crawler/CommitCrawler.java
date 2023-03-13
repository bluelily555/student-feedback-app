package com.project.feedback.crawler;

import com.project.feedback.domain.entity.RepositoryEntity;

public interface CommitCrawler {
    Commit execute(RepositoryEntity repository);
}
