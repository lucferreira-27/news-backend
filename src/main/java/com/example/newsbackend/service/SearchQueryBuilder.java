package com.example.newsbackend.service;

import com.example.newsbackend.repository.search.SearchEngine;
import org.springframework.stereotype.Service;

@Service
public class SearchQueryBuilder {
    public String build(String query, SearchEngine searchEngine) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(searchEngine.getQueryUrl() + query);
        return stringBuilder.toString();
    }
}
