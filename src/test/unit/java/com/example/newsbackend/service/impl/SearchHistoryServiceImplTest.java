package com.example.newsbackend.service;

import com.example.newsbackend.exception.ResourceNotFoundException;
import com.example.newsbackend.entity.search.SearchHistory;
import com.example.newsbackend.repository.storage.SearchHistoryRepository;
import com.example.newsbackend.entity.search.StorageResult;
import com.example.newsbackend.service.impl.ResultAnalysisServiceImpl;
import com.example.newsbackend.service.impl.SearchHistoryServiceImpl;
import com.example.newsbackend.service.impl.SearchNewsPageServiceImpl;
import com.example.newsbackend.service.impl.serp.NewsResultPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SearchHistoryServiceImplTest {

    private SearchHistoryServiceImpl searchHistoryServiceUnderTest;

    @Mock
    private SearchNewsPageServiceImpl mockSearchNewsPageService;
    @Mock
    private ResultAnalysisServiceImpl mockResultAnalysisService;
    @Mock
    private SearchHistoryRepository mockSearchHistoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        searchHistoryServiceUnderTest = new SearchHistoryServiceImpl(
                mockSearchNewsPageService,
                mockResultAnalysisService,
                mockSearchHistoryRepository
        );
    }

    @Test
    void when_Search_Should_Return_SearchHistory() {
        //Given
        String searchQuery = "test";
        Map<String,String> filters = Map.of("test", "test");
        final List<NewsResultPage> newsResultPages = List.of(new NewsResultPage(),new NewsResultPage(),new NewsResultPage());

        //When
        when(mockSearchNewsPageService.search(filters)).thenReturn(newsResultPages);
        when(mockSearchHistoryRepository.save(any(SearchHistory.class))).thenAnswer(i -> i.getArguments()[0]);
        when(mockResultAnalysisService.analysis(any(NewsResultPage.class))).thenReturn(new StorageResult());
        //Then
        SearchHistory result = searchHistoryServiceUnderTest.search(searchQuery,filters);
        //Verify
        assertThat(result).isNotNull();
        assertThat(result.getSearchTerm()).isEqualTo(searchQuery);
        assertThat(result.getStorageResults().size()).isEqualTo(newsResultPages.size());
        assertThat(result.getDate()).isCloseTo(LocalDateTime.now(), within(3, ChronoUnit.SECONDS));
        verify(mockSearchHistoryRepository,times(1)).save(any(SearchHistory.class));
        verify(mockSearchNewsPageService,times(1)).search(filters);
        verify(mockResultAnalysisService,times(3)).analysis(any(NewsResultPage.class));
    }

    @Test
    void when_FindById_Should_Return_SearchHistory_With_Given_Id() {
        //Given
        final SearchHistory searchHistory = new SearchHistory();
        searchHistory.setId(1L);
        //When
        when(mockSearchHistoryRepository.findById(1L)).thenReturn(Optional.of(searchHistory));
        //Then
        SearchHistory result = searchHistoryServiceUnderTest.findById(1L);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(mockSearchHistoryRepository,times(1)).findById(1L);
    }
    @Test
    void if_When_FindById_No_Resource_Found_Should_Throw_ResourceNotFoundException() {
        //Given
        final SearchHistory searchHistory = new SearchHistory();
        searchHistory.setId(1L);
        //When
        when(mockSearchHistoryRepository.findById(1L)).thenReturn(Optional.empty());
        //Then
        assertThrows(ResourceNotFoundException.class, () -> searchHistoryServiceUnderTest.findById(1L));
        verify(mockSearchHistoryRepository,times(1)).findById(1L);
    }
    @Test
    void when_FindAll_Should_Return_List_Of_SearchHistory() {
        //Given
        final List<SearchHistory> searchHistories = List.of(new SearchHistory(),new SearchHistory(),new SearchHistory());
        //When
        when(mockSearchHistoryRepository.findAll()).thenReturn(searchHistories);
        //Then
        List<SearchHistory> result = searchHistoryServiceUnderTest.findAll();
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(searchHistories.size());
        verify(mockSearchHistoryRepository,times(1)).findAll();
    }
    @Test
    void if_When_FindAll_No_Resources_Found_Should_Throw_ResourceNotFoundException() {

        //When
        when(mockSearchHistoryRepository.findAll()).thenReturn(List.of());
        //Then
        assertThrows(ResourceNotFoundException.class, () -> searchHistoryServiceUnderTest.findAll());
        verify(mockSearchHistoryRepository,times(1)).findAll();
    }


    @Test
    void when_DeleteById_Should_Delete_SearchHistory_With_Given_Id() {
        //Given
        final SearchHistory searchHistory = new SearchHistory();
        searchHistory.setId(1L);
        //When
        when(mockSearchHistoryRepository.existsById(1L)).thenReturn(true);
        //Then
        searchHistoryServiceUnderTest.deleteById(1L);
        verify(mockSearchHistoryRepository,times(1)).deleteById(1L);
        verify(mockSearchHistoryRepository,times(1)).existsById(1L);
    }
    @Test
    void if_When_DeleteById_No_Resource_Found_Should_Throw_ResourceNotFoundException() {
        //Given
        final SearchHistory searchHistory = new SearchHistory();
        searchHistory.setId(1L);
        //When
        when(mockSearchHistoryRepository.existsById(1L)).thenReturn(false);
        //Then
        assertThrows(ResourceNotFoundException.class, () -> searchHistoryServiceUnderTest.deleteById(1L));
        verify(mockSearchHistoryRepository,never()).deleteById(1L);
        verify(mockSearchHistoryRepository,times(1)).existsById(1L);
    }

    @Test
    void when_DeleteAll_Should_Delete_All_SearchHistories() {

        //When
        doNothing().when(mockSearchHistoryRepository).deleteAll();

        //Then
        searchHistoryServiceUnderTest.deleteAll();

        //Verify
        verify(mockSearchHistoryRepository,times(1)).deleteAll();
    }
}