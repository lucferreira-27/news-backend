package com.example.newsbackend.controller;

import com.example.newsbackend.NewsBackendApplication;
import com.example.newsbackend.entity.search.SearchHistory;
import com.example.newsbackend.entity.search.StorageResult;
import com.example.newsbackend.repository.storage.SearchHistoryRepository;
import com.example.newsbackend.repository.storage.StorageResultRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NewsBackendApplication.class)
@AutoConfigureMockMvc
public class RestControllerStorageTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private SearchHistoryRepository searchHistoryRepository;
    @Test
    public void when_Find_Should_Return_As_Response_StatusOK_With_StorageResult_Given_Id_And_SearchHistoryId() throws Exception {
        final SearchHistory searchHistory = new SearchHistory();
        searchHistory.setId(1L);
        final StorageResult storageResult = new StorageResult();
        storageResult.setId(1L);
        storageResult.setSearchHistory(searchHistory);
        searchHistoryRepository.save(searchHistory);

        mvc.perform(get("/api/v1/search/history/1/storage/1"))
                .andExpect(status().isOk());
    }
}
