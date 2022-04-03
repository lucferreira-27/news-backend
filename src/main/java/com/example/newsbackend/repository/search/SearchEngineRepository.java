package com.example.newsbackend.repository.search;

import org.springframework.data.repository.CrudRepository;

public interface SearchEngineRepository extends CrudRepository<SearchEngine, Long> {
    public Iterable<SearchEngine> findByActiveTrue();
}
