package com.example.newsbackend.service.impl;

import com.example.newsbackend.entity.search.SearchHistory;
import com.example.newsbackend.entity.search.StorageResult;
import com.example.newsbackend.exception.ResourceNotFoundException;
import com.example.newsbackend.repository.storage.StorageResultRepository;
import com.example.newsbackend.service.StorageResultService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StorageResultResultServiceImpl implements StorageResultService {

    private final StorageResultRepository storageResultRepository;

    public StorageResultResultServiceImpl(StorageResultRepository storageResultRepository) {
        this.storageResultRepository = storageResultRepository;
    }

    @Override
    public StorageResult findById(Long searchHistoryId, Long storageId) {
        Optional<StorageResult> optional = storageResultRepository.findByIdAndSearchHistoryId(storageId, searchHistoryId);
        StorageResult storageResult = optional.orElseThrow(
                () -> new ResourceNotFoundException(
                                "Storage Result with id "
                                + storageId +
                                " not found in search history with id "
                                + searchHistoryId)
        );
        return storageResult;
    }

    @Override
    public List<StorageResult> findAll() {
        List<StorageResult> storageResults = storageResultRepository.findAll();
        if(storageResults.isEmpty()) {
            throw new ResourceNotFoundException("No storage results found");
        }
        return storageResults;
    }

    @Override
    public List<StorageResult> findBySearchHistoryId(Long searchHistoryId) {
        List<StorageResult> storageResults = storageResultRepository.findAllBySearchHistoryId(searchHistoryId);
        if(storageResults.isEmpty()) {
            throw new ResourceNotFoundException("No storage results found for search history with id " + searchHistoryId);
        }
        return storageResults;
    }

    @Override
    public void deleteById(Long searchHistoryId, Long storageId) {
        StorageResult storageResult = findById(searchHistoryId, storageId);
        storageResultRepository.delete(storageResult);
    }

    @Override
    public void deleteAllBySearchHistoryId(Long searchHistoryId) {
        List<StorageResult> storageResults = findBySearchHistoryId(searchHistoryId);
        storageResultRepository.deleteAll(storageResults);
    }


}
