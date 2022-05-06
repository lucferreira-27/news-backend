package com.example.newsbackend.repository.storage;

import com.example.newsbackend.entity.search.StorageResult;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface StorageResultRepository extends CrudRepository<StorageResult, Long> {
    Optional<StorageResult> findBySearchInfoUrl(String link);
    Optional<StorageResult> findByIdAndSearchHistoryId(Long id, Long searchHistoryId);
    List<StorageResult> findAllBySearchHistoryId(Long searchHistoryId);
    List<StorageResult> findAll();
}
