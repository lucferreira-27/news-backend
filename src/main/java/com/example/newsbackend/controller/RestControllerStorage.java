package com.example.newsbackend.controller;

import com.example.newsbackend.entity.search.StorageResult;
import com.example.newsbackend.service.impl.StorageResultResultServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/search/history")
public class RestControllerStorage {
    private final StorageResultResultServiceImpl storageResultResultService;

    public RestControllerStorage(StorageResultResultServiceImpl storageResultResultService) {
        this.storageResultResultService = storageResultResultService;
    }

    @GetMapping("/{idHistory}/storage/{idStorage}")
    public ResponseEntity<?> findById(@PathVariable Long idHistory, @PathVariable Long idStorage) {
        StorageResult storageResult = storageResultResultService.findById(idHistory, idStorage);
        return ResponseEntity.ok(storageResult);
    }

    @GetMapping("/{idHistory}/storage/all")
    public ResponseEntity<?> findAllByIdHistory(@PathVariable Long idHistory) {
        List<StorageResult> storageResults = storageResultResultService.findBySearchHistoryId(idHistory);
        return ResponseEntity.ok(storageResults);
    }

    @DeleteMapping("/{idHistory}/storage/{idStorage}")
    public ResponseEntity<?> deleteById(@PathVariable Long idHistory, @PathVariable Long idStorage) {
        storageResultResultService.deleteById(idHistory, idStorage);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/{idHistory}/storage/all")
    public ResponseEntity<?> deleteAllByIdHistory(@PathVariable Long idHistory) {
        storageResultResultService.deleteAllBySearchHistoryId(idHistory);
        return ResponseEntity.accepted().build();
    }
}
