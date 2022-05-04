package com.example.newsbackend.service;

import com.example.newsbackend.repository.storage.SearchHistory;

import java.util.List;
import java.util.Map;

public interface SearchHistoryService {
    SearchHistory search(String query, Map<String, String> filters);
    SearchHistory findById(Long id);
    List<SearchHistory> findAll();
    void deleteById(Long id);
    void deleteAll();
}
