package com.example.newsbackend.controller;

import com.example.newsbackend.entity.search.StorageResult;
import com.example.newsbackend.service.impl.StorageResultServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/search/history/{idHistory}/storages")
public class RestControllerStorage {
    private final StorageResultServiceImpl storageResultResultService;

    public RestControllerStorage(StorageResultServiceImpl storageResultResultService) {
        this.storageResultResultService = storageResultResultService;
    }

    @GetMapping("/{idStorage}")
    public ResponseEntity<?> findById(@PathVariable Long idHistory, @PathVariable Long idStorage) {
        StorageResult storageResult = storageResultResultService.findById(idHistory, idStorage);
        return ResponseEntity.ok(storageResult);
    }

    @GetMapping("/")
    public ResponseEntity<?> findAllByIdHistory(@PathVariable Long idHistory) {
        List<StorageResult> storageResults = storageResultResultService.findBySearchHistoryId(idHistory);
        return ResponseEntity.ok(storageResults);
    }

    @DeleteMapping("/{idStorage}")
    public ResponseEntity<?> deleteById(@PathVariable Long idHistory, @PathVariable Long idStorage) {
        storageResultResultService.deleteById(idHistory, idStorage);
        return ResponseEntity.accepted().build();
    }


}
