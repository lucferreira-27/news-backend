package com.example.newsbackend.service;

import com.example.newsbackend.repository.storage.SearchHistory;
import com.example.newsbackend.repository.storage.SearchHistoryRepository;
import com.example.newsbackend.repository.storage.StorageResult;
import com.example.newsbackend.service.serp.NewsResultPage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SearchHistoryServiceImpl implements SearchHistoryService {

    private final SearchNewsPageServiceImpl searchNewsPageService;
    private final ResultAnalysisServiceImpl resultAnalysisService;
    private final SearchHistoryRepository searchHistoryRepository;

    public SearchHistoryServiceImpl(SearchNewsPageServiceImpl searchNewsPageService,
                                    ResultAnalysisServiceImpl resultAnalysisService, SearchHistoryRepository searchHistoryRepository) {
        this.searchNewsPageService = searchNewsPageService;
        this.resultAnalysisService = resultAnalysisService;
        this.searchHistoryRepository = searchHistoryRepository;
    }

    @Override
    public SearchHistory search(String query, Map<String, String> filters) {
        List<NewsResultPage> newsResultPages = searchNewsPageService.search(filters);
        SearchHistory searchHistory = new SearchHistory();
        searchHistory.setSearchTerm(query);
        searchHistory.setDate(LocalDateTime.now());

        for (NewsResultPage newsResultPage : newsResultPages) {
            StorageResult storageResult = resultAnalysisService.analysis(newsResultPage);
            searchHistory.getStorageResults().add(storageResult);
        }

        return searchHistoryRepository.save(searchHistory);
    }

    @Override
    public SearchHistory findById(Long id) {
        Optional<SearchHistory>  optional = searchHistoryRepository.findById(id);
        SearchHistory searchHistory = optional.orElseThrow(() -> new ResourceNotFoundException("Search history with id \"" + id +"\" not found"));
        return searchHistory;
    }

    @Override
    public List<SearchHistory> findAll() {
       List<SearchHistory> searchHistories = searchHistoryRepository.findAll();
       if(searchHistories.isEmpty()) {
           throw new ResourceNotFoundException("No search history found");
       }
        return searchHistories;
    }

    @Override
    public void deleteById(Long id) {

        if(!searchHistoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Search history with id \"" + id +"\" not found");
        }
        searchHistoryRepository.deleteById(id);

    }

    @Override
    public void deleteAll() {
        searchHistoryRepository.deleteAll();
    }


}
