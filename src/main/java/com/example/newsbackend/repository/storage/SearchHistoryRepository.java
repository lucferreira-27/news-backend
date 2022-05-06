package com.example.newsbackend.repository.storage;

import com.example.newsbackend.entity.search.SearchHistory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SearchHistoryRepository extends CrudRepository<SearchHistory, Long> {
    List<SearchHistory> findAll();
}
