package com.example.newsbackend.controller;

import com.example.newsbackend.repository.page.PageBody;
import com.example.newsbackend.service.PageValidatorException;
import com.example.newsbackend.service.ScrapeNewsPageServiceImpl;
import com.example.newsbackend.service.SearchNewsPageServiceImpl;
import com.example.newsbackend.service.scrape.ScrapingException;
import com.example.newsbackend.service.serp.NewsResultPage;
import com.example.newsbackend.service.serp.ScaleAPIException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class RestControllerSearch {

    private final SearchNewsPageServiceImpl searchNewsPageService;
    private final ScrapeNewsPageServiceImpl scrapePageService;

    public RestControllerSearch(SearchNewsPageServiceImpl searchNewsPageService, ScrapeNewsPageServiceImpl scrapePageService) {
        this.searchNewsPageService = searchNewsPageService;
        this.scrapePageService = scrapePageService;
    }

    @GetMapping("/search")
    public ResponseEntity search(@RequestParam(required = true) String q, @RequestParam Map<String, String> allRequestParams) {
        List<NewsResultPage> newsResultPages = searchNewsPageService.search(allRequestParams);

        try {
            List<PageBody> pageBodies = new ArrayList<>();
            for (NewsResultPage newsResultPage : newsResultPages) {
                try {
                    PageBody pageBody = scrapePageService.scrapeNewsPages(newsResultPage);
                    pageBodies.add(pageBody);
                } catch (PageValidatorException e) {
                    e.printStackTrace();
                }
            }
            return ResponseEntity.ok(pageBodies);

        } catch (ScaleAPIException e) {
            e.printStackTrace();
        } catch (ScrapingException e) {
            e.printStackTrace();
        }

        return ResponseEntity.internalServerError().build();
    }


}
