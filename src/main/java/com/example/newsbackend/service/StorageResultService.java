package com.example.newsbackend.service;

import com.example.newsbackend.entity.search.StorageResult;

import java.util.List;

public interface StorageResultService {
    StorageResult findById(Long searchHistoryId, Long storageId);
    List<StorageResult> findAll();
    List<StorageResult> findBySearchHistoryId(Long searchHistoryId);
    void deleteById(Long searchHistoryId, Long storageId);
    void deleteAllBySearchHistoryId(Long searchHistoryId);

}
