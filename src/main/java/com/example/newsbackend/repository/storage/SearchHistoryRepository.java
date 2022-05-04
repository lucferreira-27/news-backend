package com.example.newsbackend.repository.storage;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SearchHistoryRepository extends CrudRepository<SearchHistory, Long> {
    List<SearchHistory> findAll();
}
