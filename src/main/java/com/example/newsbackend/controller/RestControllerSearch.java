package com.example.newsbackend.controller;

import com.example.newsbackend.service.SearchNewsPageService;
import com.example.newsbackend.service.SearchNewsPageServiceImpl;
import com.example.newsbackend.service.scrape.ScrapingException;
import com.example.newsbackend.service.serp.NewsResultPage;
import com.example.newsbackend.service.serp.ScaleAPIException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class RestControllerSearch {

    private final SearchNewsPageServiceImpl searchNewsPageService;

    public RestControllerSearch(SearchNewsPageServiceImpl searchNewsPageService) {
        this.searchNewsPageService = searchNewsPageService;
    }

    @GetMapping("/search")
    public ResponseEntity search(@RequestParam(required = true) String q, @RequestParam Map<String, String> allRequestParams) {
        List<NewsResultPage> newsResultPages = searchNewsPageService.search(allRequestParams);

        try {
            return ResponseEntity
                    .ok()
                    .body(searchNewsPageService.search(allRequestParams));
        } catch (ScaleAPIException e) {
            e.printStackTrace();
        }

        return ResponseEntity.internalServerError().build();
    }


}
