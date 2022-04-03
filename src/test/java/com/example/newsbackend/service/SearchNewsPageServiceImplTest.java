package com.example.newsbackend.service;

import com.example.newsbackend.repository.page.PageContent;
import com.example.newsbackend.repository.page.PageHeadline;
import com.example.newsbackend.repository.search.SearchEngine;
import com.example.newsbackend.repository.search.SearchEngineRepository;
import com.example.newsbackend.repository.sites.SiteConfiguration;
import com.example.newsbackend.service.scrape.ScrapingException;
import com.example.newsbackend.service.scrape.ScrapingStrategy;
import com.example.newsbackend.service.scrape.dynamic.DynamicScrapingBot;
import com.example.newsbackend.service.scrape.stable.ParseValues;
import com.example.newsbackend.service.tools.PageScrapeTool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


class SearchNewsPageServiceImplTest {

    @Mock
    private SearchQueryBuilder mockSearchQueryBuilder;
    @Mock
    private PageScrapeTool mockPageScrapeTool;
    @Mock
    private SearchEngineRepository mockSearchEngineRepository;

    private SearchNewsPageServiceImpl searchNewsPageServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        searchNewsPageServiceImplUnderTest = new SearchNewsPageServiceImpl(
                mockSearchQueryBuilder,
                mockPageScrapeTool,
                mockSearchEngineRepository);
    }

    @Test
    void when_Search_Should_Return_PageHeadlineList() throws Exception {
        //Given
        final String testKeyword = "keyword";
        final String testUrl = "url";
        final String testTitle = "title";
        final String testQueryUrl = "queryUrl";

        PageHeadline resultPageHeadline = new PageHeadline(testUrl, testTitle);
        List<ParseValues> scrapeReturnParseValues = List.of(new ParseValues());
        List<PageContent> expectResult = List.of(resultPageHeadline);
        SearchEngine mockSearchEngine = mock(SearchEngine.class);

        //When
        when(mockSearchQueryBuilder.build(any(String.class), any(SearchEngine.class))).thenReturn(testQueryUrl);
        when(mockSearchEngine.getSiteConfiguration()).thenReturn(new SiteConfiguration());
        when(mockPageScrapeTool.scrape(any(SiteConfiguration.class), eq(testQueryUrl))).thenReturn(scrapeReturnParseValues);
        when(mockSearchEngineRepository.findByActiveTrue()).thenReturn(List.of(mockSearchEngine));

        MockedStatic<AttributesContent> mb = Mockito.mockStatic(AttributesContent.class);
        mb.when(() -> AttributesContent.contentToHeadline(any(ParseValues.class))).thenReturn(resultPageHeadline);

        //Then
        final List<PageHeadline> result = searchNewsPageServiceImplUnderTest.search(testKeyword);

        //Verify
        assertThat(result).isEqualTo(expectResult);
        verify(mockSearchQueryBuilder, times(1)).build(eq(testKeyword), eq(mockSearchEngine));
        verify(mockPageScrapeTool, times(1)).scrape(any(SiteConfiguration.class), eq(testQueryUrl));
        verify(mockSearchEngineRepository, times(1)).findByActiveTrue();

        mb.close();
    }

    @Test
    void if_No_Active_SearchEngine_Should_Throw_ScrapingException() throws ScrapingException {
        //Given
        final String expectedMessage = "No active search engine";

        //When
        when(mockSearchEngineRepository.findByActiveTrue()).thenReturn(Collections.emptyList());

        //Then
        ScrapingException exception = assertThrows(ScrapingException.class, () -> searchNewsPageServiceImplUnderTest.search("keyword"));

        //Verify
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);

        verify(mockSearchEngineRepository, times(1)).findByActiveTrue();
        verifyNoMoreInteractions(mockSearchEngineRepository);
        verify(mockSearchQueryBuilder, never()).build(any(String.class), any(SearchEngine.class));
        verify(mockPageScrapeTool, never()).scrape(any(SiteConfiguration.class), any(String.class));

    }


    @Test
    void when_PageScrapeTool_Throws_ScrapingException_SearchNewsPageService_Should_Throw_ScrapingException() throws Exception {

        //Given
        final String testKeyword = "keyword";
        final String testUrl = "url";

        // When
        when(mockSearchQueryBuilder.build(eq(testKeyword), any(SearchEngine.class))).thenReturn(testUrl);
        when(mockPageScrapeTool.scrape(any(SiteConfiguration.class), eq(testUrl)))
                .thenThrow(ScrapingException.class);

        //Then
        assertThrows(ScrapingException.class, () -> searchNewsPageServiceImplUnderTest.search(testKeyword));

    }
}
