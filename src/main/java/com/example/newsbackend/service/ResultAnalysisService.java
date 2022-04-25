package com.example.newsbackend.service;

import com.example.newsbackend.repository.storage.StorageResult;
import com.example.newsbackend.service.serp.NewsResultPage;

public interface ResultAnalysisService {
    public StorageResult analysis(NewsResultPage newsResultPage);
}
