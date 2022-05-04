package com.example.newsbackend.controller;

import com.example.newsbackend.repository.storage.SearchHistory;
import com.example.newsbackend.service.SearchHistoryServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class RestControllerSearch {

    private final SearchHistoryServiceImpl searchHistoryPageService;

    public RestControllerSearch(SearchHistoryServiceImpl searchHistoryPageService) {
        this.searchHistoryPageService = searchHistoryPageService;
    }

    @GetMapping("/search")
    public ResponseEntity<SearchHistory> search(@RequestParam String q, @RequestParam Map<String, String> allRequestParams) {

        SearchHistory searchHistory = searchHistoryPageService.search(q, allRequestParams);
        return ResponseEntity.ok(searchHistory);


    }

    @GetMapping("/search/history/find/{id}")
    public ResponseEntity<SearchHistory> findSearchHistory(@PathVariable Long id) {
        SearchHistory searchHistory = searchHistoryPageService.findById(id);
        return ResponseEntity.ok(searchHistory);

    }

    @DeleteMapping("/search/history/delete/{id}")
    public ResponseEntity<SearchHistory> deleteSearchHistory(@PathVariable Long id) {
        searchHistoryPageService.deleteById(id);
        return ResponseEntity.accepted().build();

    }

    @GetMapping("/search/history/find/all")
    public ResponseEntity<List<SearchHistory>> findAllSearchHistory() {
        List<SearchHistory> searchHistoryList = searchHistoryPageService.findAll();
        return ResponseEntity.ok(searchHistoryList);

    }

    @DeleteMapping("/search/history/delete/all")
    public ResponseEntity<SearchHistory> deleteAllSearchHistory() {
        searchHistoryPageService.deleteAll();
        return ResponseEntity.accepted().build();

    }


}
