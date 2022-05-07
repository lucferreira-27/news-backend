package com.example.newsbackend.controller;

import com.example.newsbackend.NewsBackendApplication;
import com.example.newsbackend.entity.search.SearchHistory;
import com.example.newsbackend.entity.search.StorageResult;
import com.example.newsbackend.entity.sites.RegisteredSite;
import com.example.newsbackend.repository.storage.SearchHistoryRepository;
import com.example.newsbackend.repository.storage.StorageResultRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
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
    @Autowired
    private StorageResultRepository storageResultRepository;
    @BeforeEach
    public void beforeEach() {
        searchHistoryRepository.deleteAll();
        storageResultRepository.deleteAll();
    }

    @Test
    public void when_Find_Should_Return_As_Response_StatusOK_With_StorageResult_Given_Id_And_SearchHistoryId() throws Exception {
        //Given

        final SearchHistory searchHistory = new SearchHistory();
        final StorageResult storageResult = new StorageResult();
        storageResult.setSearchHistory(searchHistory);
        searchHistory.getStorageResults().add(storageResult);
        SearchHistory findSearchHistory = searchHistoryRepository.save(searchHistory);

        mvc.perform(get("/api/v1/search/history/"+findSearchHistory.getId()+"/storages/"+
                        findSearchHistory.getStorageResults().get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(findSearchHistory.getStorageResults().get(0).getId()));
    }
    @Test
    public void when_Find_If_Not_Resource_Found_Should_Return_As_Response_StatusNotFound_Given_Id_And_SearchHistoryId() throws Exception {

        mvc.perform(get("/api/v1/search/history/0/storages/0"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Storage Result with id "
                        + 0 + " not found in search history with id " + 0));
    }
    @Test
    public void when_Find_All_Should_Return_As_Response_StatusOK_With_List_Of_StorageResult_Given_SearchHistoryId() throws Exception {

        //Given

        final SearchHistory searchHistory = new SearchHistory();
        final StorageResult storageResult = new StorageResult();
        storageResult.setSearchHistory(searchHistory);
        final StorageResult storageResult2 = new StorageResult();
        storageResult2.setSearchHistory(searchHistory);
        final StorageResult storageResult3 = new StorageResult();
        storageResult3.setSearchHistory(searchHistory);
        List<StorageResult> storageResults = List.of(storageResult,storageResult2,storageResult3);
        searchHistory.getStorageResults().addAll(storageResults);
        SearchHistory findSearchHistory =  searchHistoryRepository.save(searchHistory);

         mvc.perform(get("/api/v1/search/history/"+findSearchHistory.getId() +"/storages/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(findSearchHistory.getStorageResults().size()));
    }
    @Test
    public void when_Find_All_If_Not_Resource_Found_Should_Return_As_Response_StatusNotFound_Given_SearchHistoryId() throws Exception {

         mvc.perform(get("/api/v1/search/history/0/storages/"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No storage results found for search history with id " + 0L));
    }
    @Test
    public void when_DeleteById_Should_Return_As_Response_StatusOk_Given_Id_And_SearchHistoryId() throws Exception {
        //Given
        final long searchHistoryId = 1L;
        final long storageResultId = 1L;
        StorageResult storageResult = new StorageResult();
        storageResult.setId(storageResultId);
        SearchHistory searchHistory = new SearchHistory();
        searchHistory.setId(searchHistoryId);
        storageResult.setSearchHistory(searchHistory);
        searchHistory.getStorageResults().add(storageResult);
        searchHistoryRepository.save(searchHistory);

        //Verify
        mvc.perform(delete("/api/v1/search/history/"+searchHistoryId+"/storages/"+storageResultId))
                .andExpect(status().isAccepted());
    }

}
