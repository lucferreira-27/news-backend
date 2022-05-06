package com.example.newsbackend.service.impl;

import com.example.newsbackend.exception.ScaleAPIException;
import com.example.newsbackend.service.impl.SearchNewsPageServiceImpl;
import com.example.newsbackend.service.impl.serp.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
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
    private ScaleAPIRequestServiceImpl mockScaleAPIRequestServiceImpl;

    private SearchNewsPageServiceImpl searchNewsPageServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        searchNewsPageServiceImplUnderTest = new SearchNewsPageServiceImpl(mockScaleAPIRequestServiceImpl);
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
        when(mockScaleAPIRequestServiceImpl.getResponse(any(RequestParameters.class))).thenReturn(apiResponse);
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
        when(mockScaleAPIRequestServiceImpl.getResponse(any(RequestParameters.class))).thenReturn(apiResponse);
        ReflectionTestUtils.setField(searchNewsPageServiceImplUnderTest, "apikey", null);
        //Then
        assertThatThrownBy(() -> searchNewsPageServiceImplUnderTest.search(Map.of("q", testKeyword)))
                .isInstanceOf(IllegalArgumentException.class);

        //Verify
        verify(mockScaleAPIRequestServiceImpl, never()).getResponse(any(RequestParameters.class));


    }

    @Test
    void if_No_Result_Are_Found_Should_Throw_ScaleAPIException() {
        //Given
        final String testKeyword = "keyword";

        ScaleAPIResponse apiResponse = mock(ScaleAPIResponse.class);

        //When
        when(mockScaleAPIRequestServiceImpl.getResponse(any(RequestParameters.class))).thenReturn(apiResponse);
        ReflectionTestUtils.setField(searchNewsPageServiceImplUnderTest, "apikey", "randomValue");
        when(apiResponse.getNewsResultPages()).thenReturn(Collections.emptyList());
        //Then
        assertThatThrownBy(() -> searchNewsPageServiceImplUnderTest.search(Map.of("q", testKeyword)))
                .isInstanceOf(ScaleAPIException.class)
                .hasMessage("No results found for query \"" + testKeyword + "\"");

        //Verify
        verify(mockScaleAPIRequestServiceImpl, times(1)).getResponse(any(RequestParameters.class));
        verify(apiResponse, times(1)).getNewsResultPages();


    }
}
