package com.example.newsbackend.service;

import com.example.newsbackend.entity.sites.SelectorQuery;
import com.example.newsbackend.entity.sites.SiteConfiguration;
import com.example.newsbackend.service.impl.PageScrapeServiceImpl;
import com.example.newsbackend.exception.ScrapingException;
import com.example.newsbackend.service.impl.scrape.ScrapingStrategyAbstract;
import com.example.newsbackend.service.impl.scrape.dynamic.DynamicScrapingBot;
import com.example.newsbackend.service.impl.scrape.stable.ParseValues;
import com.example.newsbackend.service.impl.scrape.stable.StaticScrapingBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class PagePageScrapeServiceTest {


    @Mock
    private StaticScrapingBot mockStaticScrapingBot;
    @Mock
    private DynamicScrapingBot mockDynamicScrapingBot;
    @Spy
    private Map<String, ScrapingStrategyAbstract> mockScrapingStrategies = new HashMap<>(2);

    private PageScrapeServiceImpl pageScrapeServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockScrapingStrategies.put("STATIC", mockStaticScrapingBot);
        mockScrapingStrategies.put("DYNAMIC", mockDynamicScrapingBot);
        pageScrapeServiceImplUnderTest = new PageScrapeServiceImpl(mockScrapingStrategies);


    }


    @Test
    void when_PageScrape_Should_Get_StaticScrapingBot_From_MapScrapingStrategies() throws ScrapingException {
        // Given
        final String testUrl = "url";
        final SiteConfiguration siteConfiguration = createSiteConfiguration();
        final SelectorQuery selectorQuery = createScrapeQuery();
        siteConfiguration.setSelectorQueries(List.of(selectorQuery));
        siteConfiguration.setScrapingType(SiteConfiguration.ScrapingType.STATIC);

        // Then
        pageScrapeServiceImplUnderTest.scrape(siteConfiguration, testUrl);

        // Verify
        verify(mockScrapingStrategies, times(1)).get("STATIC");
        verify(mockStaticScrapingBot, times(1)).extractPageContents(any(String.class), any(SiteConfiguration.class));
        verify(mockDynamicScrapingBot, never()).extractPageContents(any(String.class), any(SiteConfiguration.class));


    }

    @Test
    void when_PageScrape_Should_Get_DynamicScrapingBot_From_MapScrapingStrategies() throws ScrapingException {
        // Given
        final String testUrl = "url";
        final SiteConfiguration siteConfiguration = createSiteConfiguration();
        final SelectorQuery selectorQuery = createScrapeQuery();
        siteConfiguration.setSelectorQueries(List.of(selectorQuery));
        siteConfiguration.setScrapingType(SiteConfiguration.ScrapingType.DYNAMIC);

        // Then
        pageScrapeServiceImplUnderTest.scrape(siteConfiguration, testUrl);

        // Verify
        verify(mockScrapingStrategies, times(1)).get("DYNAMIC");
        verify(mockDynamicScrapingBot, times(1)).extractPageContents(any(String.class), any(SiteConfiguration.class));
        verify(mockStaticScrapingBot, never()).extractPageContents(any(String.class), any(SiteConfiguration.class));


    }

    @Test
    void when_Scrape_Should_Return_List_of_PageContent() throws Exception {
        // Given
        final SiteConfiguration siteConfiguration = createSiteConfiguration();
        final SelectorQuery selectorQuery = createScrapeQuery();
        final List<ParseValues> expectResult = List.of(new ParseValues());
        siteConfiguration.setSelectorQueries(List.of(selectorQuery));
        siteConfiguration.setScrapingType(SiteConfiguration.ScrapingType.STATIC);
        // When
        when(mockStaticScrapingBot.extractPageContents(any(String.class), any(SiteConfiguration.class))).thenReturn(expectResult);

        // Then
        final List<ParseValues> result = pageScrapeServiceImplUnderTest.scrape(siteConfiguration, "url");

        // Verify
        assertThat(result).isEqualTo(expectResult);

        verify(mockStaticScrapingBot, times(1)).extractPageContents(any(String.class), any(SiteConfiguration.class));

    }


    @Test
    void if_Map_Returns_Null_Should_Throw_NullPointException() throws Exception {
        // Given
        final String testUrl = "url";
        final SiteConfiguration siteConfiguration = createSiteConfiguration();
        final SelectorQuery selectorQuery = createScrapeQuery();
        siteConfiguration.setSelectorQueries(List.of(selectorQuery));
        siteConfiguration.setScrapingType(SiteConfiguration.ScrapingType.STATIC);

        // When
        doReturn(null).when(mockScrapingStrategies).get(siteConfiguration.getScrapingType().name());

        // Verify
        assertThrows(NullPointerException.class, () -> pageScrapeServiceImplUnderTest.scrape(siteConfiguration, testUrl));
        verify(mockStaticScrapingBot, never()).extractPageContents(any(String.class), any(SiteConfiguration.class));

    }
    @Test
    void if_SelectorQueryList_is_Empty_Should_Throw_NullPointException() throws Exception {
        // Given
        final String testUrl = "url";
        final SiteConfiguration siteConfiguration = createSiteConfiguration();
        siteConfiguration.setSelectorQueries(Collections.emptyList());
        siteConfiguration.setScrapingType(SiteConfiguration.ScrapingType.STATIC);


        // Verify
        assertThrows(NullPointerException.class, () -> pageScrapeServiceImplUnderTest.scrape(siteConfiguration, testUrl));

        verify(mockStaticScrapingBot, never()).extractPageContents(any(String.class), any(SiteConfiguration.class));
    }

    @Test
    void when_Scrape_Should_Throw_ScrapingException() throws ScrapingException {
        // Given
        final String testUrl = "url";
        final SiteConfiguration siteConfiguration = createSiteConfiguration();
        final SelectorQuery selectorQuery = createScrapeQuery();
        siteConfiguration.setSelectorQueries(List.of(selectorQuery));
        siteConfiguration.setScrapingType(SiteConfiguration.ScrapingType.STATIC);
        final ScrapingException scrapingException = new ScrapingException("test");

        // When
        doThrow(scrapingException).when(mockStaticScrapingBot).extractPageContents(any(String.class), any(SiteConfiguration.class));

        // Verify
        assertThrows(ScrapingException.class, () -> pageScrapeServiceImplUnderTest.scrape(siteConfiguration, testUrl));

    }

    private SiteConfiguration createSiteConfiguration() {
        SiteConfiguration siteConfiguration = new SiteConfiguration();
        siteConfiguration.setId(0L);
        siteConfiguration.setCountry("siteCountry");
        siteConfiguration.setDescription("siteDescription");
        siteConfiguration.setDomain("siteDomain");
        siteConfiguration.setLanguage("siteLanguage");
        siteConfiguration.setLogo("siteLogo");
        siteConfiguration.setKeywords(List.of("siteKeywords"));
        siteConfiguration.setName("siteName");
        return siteConfiguration;
    }

    private SelectorQuery createScrapeQuery() {
        SelectorQuery selectorQuery = new SelectorQuery();
        selectorQuery.setId(0L);

        return selectorQuery;
    }
}
