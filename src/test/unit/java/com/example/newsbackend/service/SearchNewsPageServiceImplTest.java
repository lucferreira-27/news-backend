package com.example.newsbackend.service;

import com.example.newsbackend.repository.page.PageContent;
import com.example.newsbackend.repository.page.PageHeadline;
import com.example.newsbackend.repository.sites.SiteConfiguration;
import com.example.newsbackend.service.scrape.ScrapingException;
import com.example.newsbackend.service.scrape.ScrapingStrategy;
import com.example.newsbackend.service.scrape.dynamic.DynamicScrapingBot;
import com.example.newsbackend.service.scrape.stable.ParseValues;
import com.example.newsbackend.service.serp.*;
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
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


class SearchNewsPageServiceImplTest {


    @Mock
    private ScaleAPIRequest mockScaleAPIRequest;

    private SearchNewsPageServiceImpl searchNewsPageServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        searchNewsPageServiceImplUnderTest = new SearchNewsPageServiceImpl(mockScaleAPIRequest);
    }

    @Test
    void when_Search_Should_Return_PageHeadlineList() {
        //Given
        final String testKeyword = "keyword";
        final String testUrl = "url";
        final String testTitle = "title";

        NewsResultPage resultPageHeadline = new NewsResultPage(testTitle, testUrl);
        List<NewsResultPage> expectResult = List.of(resultPageHeadline);
        ScaleAPIResponse apiResponse = new ScaleAPIResponse();
        apiResponse.setNewsResultPages(expectResult);

        //When
        when(mockScaleAPIRequest.getResponse(any(RequestParameters.class))).thenReturn(apiResponse);
        ReflectionTestUtils.setField(searchNewsPageServiceImplUnderTest, "apikey", "randomValue");
        //Then
        final List<NewsResultPage> result = searchNewsPageServiceImplUnderTest.search(Map.of("q", testKeyword));

        //Verify
        assertThat(result).isEqualTo(expectResult);


    }

    @Test
    void if_Apikey_is_Null_Should_Throw_IllegalArgumentException() {
        //Given
        final String testKeyword = "keyword";
        final String testUrl = "url";
        final String testTitle = "title";

        NewsResultPage resultPageHeadline = new NewsResultPage(testTitle, testUrl);
        List<NewsResultPage> expectResult = List.of(resultPageHeadline);
        ScaleAPIResponse apiResponse = new ScaleAPIResponse();
        apiResponse.setNewsResultPages(expectResult);

        //When
        when(mockScaleAPIRequest.getResponse(any(RequestParameters.class))).thenReturn(apiResponse);
        ReflectionTestUtils.setField(searchNewsPageServiceImplUnderTest, "apikey", null);
        //Then
        assertThatThrownBy(() -> searchNewsPageServiceImplUnderTest.search(Map.of("q", testKeyword)))
                .isInstanceOf(IllegalArgumentException.class);

        //Verify
        verify(mockScaleAPIRequest, never()).getResponse(any(RequestParameters.class));


    }

    @Test
    void if_No_Result_Are_Found_Should_Throw_ScaleAPIException() {
        //Given
        final String testKeyword = "keyword";

        ScaleAPIResponse apiResponse = mock(ScaleAPIResponse.class);

        //When
        when(mockScaleAPIRequest.getResponse(any(RequestParameters.class))).thenReturn(apiResponse);
        ReflectionTestUtils.setField(searchNewsPageServiceImplUnderTest, "apikey", "randomValue");
        when(apiResponse.getNewsResultPages()).thenReturn(Collections.emptyList());
        //Then
        assertThatThrownBy(() -> searchNewsPageServiceImplUnderTest.search(Map.of("q", testKeyword)))
                .isInstanceOf(ScaleAPIException.class)
                .hasMessage("No results found for query \"" + testKeyword + "\"");

        //Verify
        verify(mockScaleAPIRequest, times(1)).getResponse(any(RequestParameters.class));
        verify(apiResponse, times(1)).getNewsResultPages();


    }
}
