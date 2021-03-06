package com.example.newsbackend.controller;

import com.example.newsbackend.entity.search.SearchHistory;
import com.example.newsbackend.service.impl.SearchHistoryServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/histories")
public class RestControllerSearch {

    private final SearchHistoryServiceImpl searchHistoryPageService;

    public RestControllerSearch(SearchHistoryServiceImpl searchHistoryPageService) {
        this.searchHistoryPageService = searchHistoryPageService;
    }

    @PostMapping("/search")
    public ResponseEntity<SearchHistory> search(@RequestParam String q, @RequestParam Map<String, String> allRequestParams) {

        SearchHistory searchHistory = searchHistoryPageService.search(q, allRequestParams);
        return ResponseEntity.ok(searchHistory);


    }

    @GetMapping("/{id}")
    public ResponseEntity<SearchHistory> findSearchHistory(@PathVariable Long id) {
        SearchHistory searchHistory = searchHistoryPageService.findById(id);
        return ResponseEntity.ok(searchHistory);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SearchHistory> deleteSearchHistory(@PathVariable Long id) {
        searchHistoryPageService.deleteById(id);
        return ResponseEntity.accepted().build();

    }

    @GetMapping("/")
    public ResponseEntity<List<SearchHistory>> findAllSearchHistory() {
        List<SearchHistory> searchHistoryList = searchHistoryPageService.findAll();
        return ResponseEntity.ok(searchHistoryList);

    }



}
