package com.example.newsbackend.controller;

import com.example.newsbackend.repository.storage.StorageResult;
import com.example.newsbackend.service.ResultAnalysisServiceImpl;
import com.example.newsbackend.service.SearchNewsPageServiceImpl;
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
    private final ResultAnalysisServiceImpl resultAnalysisService;
    public RestControllerSearch(SearchNewsPageServiceImpl searchNewsPageService, ResultAnalysisServiceImpl resultAnalysisService) {
        this.searchNewsPageService = searchNewsPageService;
        this.resultAnalysisService = resultAnalysisService;
    }

    @GetMapping("/search")
    public ResponseEntity search(@RequestParam(required = true) String q, @RequestParam Map<String, String> allRequestParams) {
        List<NewsResultPage> newsResultPages = searchNewsPageService.search(allRequestParams);
        try {
            List<StorageResult> storageResults = new ArrayList<>();
            for (NewsResultPage newsResultPage : newsResultPages) {
                StorageResult storageResult = resultAnalysisService.analysis(newsResultPage);
                storageResults.add(storageResult);
            }
            return ResponseEntity.ok(storageResults);

        } catch (ScaleAPIException e) {
            e.printStackTrace();
        }

        return ResponseEntity.internalServerError().build();
    }


}
