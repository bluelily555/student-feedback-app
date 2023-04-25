package com.project.feedback.infra.incoming.controller.ui;

import com.project.feedback.crawler.Commit;
import com.project.feedback.domain.dto.commitCrawler.CommitCrawlerRequest;
import com.project.feedback.infra.outgoing.entity.RepositoryEntity;
import com.project.feedback.application.CommitCrawlerService;
import com.project.feedback.application.FindService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/crawler")
public class CommitCrawlerController {
    private final FindService findService;
    private final CommitCrawlerService commitCrawlerService;

    @GetMapping
    public String crawl(CommitCrawlerRequest request, Model model) {
        model.addAttribute("repositoryNames", findService.findAllRepositoryNames());

        String lastCrawlDateTime = CommitCrawlerService.crawlDate.getYYMMDDHHMMSS();
        model.addAttribute("lastCrawlDateTime", lastCrawlDateTime);

        if (request.isEmpty()) return "crawler/index";

        List<RepositoryEntity> repository = findService.findRepositoryByName(request.getRepositoryName());

        List<Commit> commits = commitCrawlerService.crawl(repository);

        model.addAttribute("selectedName", request.getRepositoryName());
        model.addAttribute("commits", commits);
        return "crawler/index";
    }
}
