package com.example.newsbackend.repository.storage;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StorageResultRepository extends CrudRepository<StorageResult, Long> {
    Optional<StorageResult> findBySearchInfoUrl(String link);
}
