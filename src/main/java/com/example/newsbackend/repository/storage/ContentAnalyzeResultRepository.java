package com.example.newsbackend.repository.storage;

import com.example.newsbackend.entity.nlu.ContentAnaliseResult;
import org.springframework.data.repository.CrudRepository;

public interface ContentAnalyzeResultRepository extends CrudRepository<ContentAnaliseResult, Long> {
}
