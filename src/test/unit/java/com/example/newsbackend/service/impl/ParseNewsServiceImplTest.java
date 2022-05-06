package com.example.newsbackend.service;

import com.example.newsbackend.exception.PageValidatorException;
import com.example.newsbackend.service.impl.scrape.PageBody;
import com.example.newsbackend.entity.sites.RegisteredSite;
import com.example.newsbackend.service.impl.PageValidatorServiceImpl;
import com.example.newsbackend.service.impl.ParseNewsServiceImpl;
import com.example.newsbackend.service.impl.ScrapeNewsPageServiceImpl;
import com.example.newsbackend.exception.ScrapingException;
import com.example.newsbackend.service.impl.serp.NewsResultPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ParseNewsServiceImplTest {

    private ParseNewsServiceImpl parseNewsServiceUnderTest;

    @Mock
    private ScrapeNewsPageServiceImpl mockScrapeNewsPageService;

    @Mock
    private PageValidatorServiceImpl mockPageValidatorServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        parseNewsServiceUnderTest = new ParseNewsServiceImpl(
                mockScrapeNewsPageService,
                mockPageValidatorServiceImpl);
    }

    @Test
    void when_Validate_Page_Should_Return_RegisteredSite() throws PageValidatorException {
        //Given
        final RegisteredSite registeredSite = new RegisteredSite();
        final String testUrl = "https://www.google.com";
        // When
        when(mockPageValidatorServiceImpl
                .validatePage(anyString()))
                .thenReturn(registeredSite);
        // Then
        RegisteredSite resultRegisteredSite = parseNewsServiceUnderTest.validateNewsSite(testUrl);

        //Verify
        assertEquals(registeredSite, resultRegisteredSite);
        verify(mockPageValidatorServiceImpl,times(1)).validatePage(testUrl);
    }
    @Test
    void when_Get_News_PageBody_Should_Return_PageBody() throws PageValidatorException, ScrapingException {
        //Given
        final RegisteredSite registeredSite = new RegisteredSite();
        final NewsResultPage newsResultPage = new NewsResultPage();
        final String bodyContent = "body content";
        final PageBody pageBody = new PageBody(newsResultPage, bodyContent, registeredSite);
        // When
        when(mockScrapeNewsPageService.scrapeNewsPages(any(NewsResultPage.class), any(RegisteredSite.class)))
                .thenReturn(pageBody);
        // Then
        PageBody result = parseNewsServiceUnderTest.getNewsPageBody(newsResultPage, registeredSite);

        //Verify
        assertEquals(pageBody, result);
        verify(mockScrapeNewsPageService, times(1)).scrapeNewsPages(newsResultPage, registeredSite);
    }
}