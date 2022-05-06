package com.example.newsbackend.service;

import com.example.newsbackend.entity.search.StorageResult;

public interface StorageService {
    StorageResult findById(Long searchHistoryId, Long storageId);
    StorageResult findAll(Long searchHistoryId);
}
