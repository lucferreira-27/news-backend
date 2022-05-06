package com.example.newsbackend.service.impl;

import com.example.newsbackend.entity.search.StorageResult;
import com.example.newsbackend.repository.storage.StorageResultRepository;
import com.example.newsbackend.service.StorageService;

public class StorageServiceImpl implements StorageService {

    private final StorageResultRepository storageResultRepository;

    @Override
    public StorageResult findById(Long searchHistoryId, Long storageId) {
        return null;
    }

    @Override
    public StorageResult findAll(Long searchHistoryId) {
        return null;
    }
}
