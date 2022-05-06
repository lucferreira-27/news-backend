package com.example.newsbackend.service;

import com.example.newsbackend.entity.search.StorageResult;
import com.example.newsbackend.service.impl.serp.NewsResultPage;

public interface ResultAnalysisService {
    public StorageResult analysis(NewsResultPage newsResultPage);
}
