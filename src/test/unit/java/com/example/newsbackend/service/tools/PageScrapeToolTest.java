package com.example.newsbackend.service.tools;

import com.example.newsbackend.repository.sites.SelectorQuery;
import com.example.newsbackend.repository.sites.SiteConfiguration;
import com.example.newsbackend.service.scrape.ScrapingException;
import com.example.newsbackend.service.scrape.ScrapingStrategy;
import com.example.newsbackend.service.scrape.dynamic.DynamicScrapingBot;
import com.example.newsbackend.service.scrape.stable.ParseValues;
import com.example.newsbackend.service.scrape.stable.StaticScrapingBot;
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


class PageScrapeToolTest {


    @Mock
    private StaticScrapingBot mockStaticScrapingBot;
    @Mock
    private DynamicScrapingBot mockDynamicScrapingBot;
    @Spy
    private Map<String, ScrapingStrategy> mockScrapingStrategies = new HashMap<>(2);

    private PageScrapeTool pageScrapeToolUnderTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockScrapingStrategies.put("STATIC", mockStaticScrapingBot);
        mockScrapingStrategies.put("DYNAMIC", mockDynamicScrapingBot);
        pageScrapeToolUnderTest = new PageScrapeTool(mockScrapingStrategies);


    }


    @Test
    void when_PageScrape_Should_Get_StaticScrapingBot_From_MapScrapingStrategies() throws ScrapingException {
        // Given
        final String testUrl = "url";
        final SiteConfiguration siteConfiguration = createSiteConfiguration();
        final SelectorQuery selectorQuery = createScrapeQuery();
        siteConfiguration.setSelectorQueries(List.of(selectorQuery));
        siteConfiguration.setScrapingType(SiteConfiguration.DefaultScrapingType.STATIC);

        // Then
        pageScrapeToolUnderTest.scrape(siteConfiguration, testUrl);

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
        siteConfiguration.setScrapingType(SiteConfiguration.DefaultScrapingType.DYNAMIC);

        // Then
        pageScrapeToolUnderTest.scrape(siteConfiguration, testUrl);

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
        siteConfiguration.setScrapingType(SiteConfiguration.DefaultScrapingType.STATIC);
        // When
        when(mockStaticScrapingBot.extractPageContents(any(String.class), any(SiteConfiguration.class))).thenReturn(expectResult);

        // Then
        final List<ParseValues> result = pageScrapeToolUnderTest.scrape(siteConfiguration, "url");

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
        siteConfiguration.setScrapingType(SiteConfiguration.DefaultScrapingType.STATIC);

        // When
        doReturn(null).when(mockScrapingStrategies).get(siteConfiguration.getScrapingType().name());

        // Verify
        assertThrows(NullPointerException.class, () -> pageScrapeToolUnderTest.scrape(siteConfiguration, testUrl));
        verify(mockStaticScrapingBot, never()).extractPageContents(any(String.class), any(SiteConfiguration.class));

    }
    @Test
    void if_SelectorQueryList_is_Empty_Should_Throw_NullPointException() throws Exception {
        // Given
        final String testUrl = "url";
        final SiteConfiguration siteConfiguration = createSiteConfiguration();
        siteConfiguration.setSelectorQueries(Collections.emptyList());
        siteConfiguration.setScrapingType(SiteConfiguration.DefaultScrapingType.STATIC);


        // Verify
        assertThrows(NullPointerException.class, () -> pageScrapeToolUnderTest.scrape(siteConfiguration, testUrl));

        verify(mockStaticScrapingBot, never()).extractPageContents(any(String.class), any(SiteConfiguration.class));
    }

    @Test
    void when_Scrape_Should_Throw_ScrapingException() throws ScrapingException {
        // Given
        final String testUrl = "url";
        final SiteConfiguration siteConfiguration = createSiteConfiguration();
        final SelectorQuery selectorQuery = createScrapeQuery();
        siteConfiguration.setSelectorQueries(List.of(selectorQuery));
        siteConfiguration.setScrapingType(SiteConfiguration.DefaultScrapingType.STATIC);
        final ScrapingException scrapingException = new ScrapingException("test");

        // When
        doThrow(scrapingException).when(mockStaticScrapingBot).extractPageContents(any(String.class), any(SiteConfiguration.class));

        // Verify
        assertThrows(ScrapingException.class, () -> pageScrapeToolUnderTest.scrape(siteConfiguration, testUrl));

    }

    private SiteConfiguration createSiteConfiguration() {
        SiteConfiguration siteConfiguration = new SiteConfiguration();
        siteConfiguration.setId(0L);
        siteConfiguration.setSiteCountry("siteCountry");
        siteConfiguration.setSiteDescription("siteDescription");
        siteConfiguration.setSiteDomain("siteDomain");
        siteConfiguration.setSiteLanguage("siteLanguage");
        siteConfiguration.setSiteLogo("siteLogo");
        siteConfiguration.setSiteKeywords("siteKeywords");
        siteConfiguration.setSiteName("siteName");
        return siteConfiguration;
    }

    private SelectorQuery createScrapeQuery() {
        SelectorQuery selectorQuery = new SelectorQuery();
        selectorQuery.setId(0L);

        return selectorQuery;
    }
}
