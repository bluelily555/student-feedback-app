package com.project.feedback.crawler;

import java.util.Collection;
import java.util.List;

public interface CommitCrawler {
    List<Commit> execute(Collection<String> addresses) throws Exception;
}
