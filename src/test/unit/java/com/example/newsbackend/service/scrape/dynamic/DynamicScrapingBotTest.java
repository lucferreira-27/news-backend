package com.example.newsbackend.service.scrape.dynamic;

import com.example.newsbackend.repository.sites.SelectorQuery;
import com.example.newsbackend.repository.sites.SiteConfiguration;
import com.example.newsbackend.service.scrape.ScrapingException;
import com.example.newsbackend.service.scrape.stable.HtmlParser;
import com.example.newsbackend.service.scrape.stable.ParseValues;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DynamicScrapingBotTest {

    @Mock
    private FirefoxSeleniumBrowser mockEmuBrowser;
    @Mock
    private HtmlParser mockHtmlParser;
    private DynamicScrapingBot dynamicScrapingBotUnderTest;

    @BeforeEach
    void setUp() {
        dynamicScrapingBotUnderTest = new DynamicScrapingBot(mockHtmlParser, mockEmuBrowser);
    }

    @Test
    void when_ExtractPageContents_Should_Return_List_Of_ParseValues() throws Exception {
        // Given
        final List<ParseValues> expectParseValues = List.of(new ParseValues());
        final String testContentExtract = "contentExtract";
        final String testUrl = "url";
        final SiteConfiguration siteConfiguration = createSiteConfiguration();
        final SelectorQuery selectorQuery = createScrapeQuery();
        final List<SelectorQuery> scrapeQueries = List.of(selectorQuery);

        siteConfiguration.setSelectorQueries(List.of(selectorQuery));
        siteConfiguration.setScrapingType(SiteConfiguration.DefaultScrapingType.DYNAMIC);

        DynamicScrapingBot spyDynamicScrapingBot = spy(dynamicScrapingBotUnderTest);

        // When
        doReturn(testContentExtract).when(spyDynamicScrapingBot).scrapePage(testUrl);
        doReturn(expectParseValues).when(mockHtmlParser).parse(testContentExtract, scrapeQueries);

        // Then
        final List<ParseValues> result = spyDynamicScrapingBot.extractPageContents(testUrl, siteConfiguration);

        // Verify
        assertThat(result).isEqualTo(expectParseValues);
        verify(mockHtmlParser, times(1)).parse(any(String.class), anyList());
        verify(spyDynamicScrapingBot, times(1)).scrapePage(any(String.class));
        verifyNoMoreInteractions(mockHtmlParser);


    }

    @Test
    void if_ScrapePage_Fail_Should_Throw_ScrapingException() throws Exception {
        // Given
        final String testUrl = "url";
        final SiteConfiguration siteConfiguration = createSiteConfiguration();
        final SelectorQuery selectorQuery = createScrapeQuery();
        siteConfiguration.setSelectorQueries(List.of(selectorQuery));
        siteConfiguration.setScrapingType(SiteConfiguration.DefaultScrapingType.DYNAMIC);
        DynamicScrapingBot spyDynamicScrapingBot = spy(dynamicScrapingBotUnderTest);

        // When
        doThrow(EmuBrowserException.class).when(spyDynamicScrapingBot).scrapePage(testUrl);

        // Then
        assertThatThrownBy(() -> spyDynamicScrapingBot.extractPageContents(testUrl, siteConfiguration))
                .isInstanceOf(ScrapingException.class);

        // Verify
        verify(spyDynamicScrapingBot, times(1)).scrapePage(any(String.class));
        verify(mockHtmlParser, never()).parse(any(String.class), anyList());
    }

    @Test
    void when_ScrapePage_Should_Return_Extracted_Content() throws EmuBrowserException {
        // Given
        final String testUrl = "url";
        final SiteConfiguration siteConfiguration = createSiteConfiguration();
        final SelectorQuery selectorQuery = createScrapeQuery();
        final String testContentExtract = "contentExtract";
        siteConfiguration.setSelectorQueries(List.of(selectorQuery));
        siteConfiguration.setScrapingType(SiteConfiguration.DefaultScrapingType.STATIC);

        // When
        doNothing().when(mockEmuBrowser).navigate(testUrl);
        when(mockEmuBrowser.retrieveContent()).thenReturn(testContentExtract);

        // Then
        final String result = dynamicScrapingBotUnderTest.scrapePage(testUrl);

        // Verify
        assertThat(result).isEqualTo(testContentExtract);
        verify(mockEmuBrowser, times(1)).navigate(any(String.class));
        verify(mockEmuBrowser, times(1)).retrieveContent();
        verify(mockHtmlParser, never()).parse(any(String.class), anyList());

    }

    @Test
    void if_ScrapePage_Fail_When_RetrieveContent_Should_Throw_EmuBrowserException() throws Exception {
        // Given
        final String testUrl = "url";
        final SiteConfiguration siteConfiguration = createSiteConfiguration();
        final SelectorQuery selectorQuery = createScrapeQuery();
        siteConfiguration.setSelectorQueries(List.of(selectorQuery));
        siteConfiguration.setScrapingType(SiteConfiguration.DefaultScrapingType.STATIC);

        // When
        doThrow(EmuBrowserException.class).when(mockEmuBrowser).retrieveContent();

        // Then
        assertThatThrownBy(() -> dynamicScrapingBotUnderTest.scrapePage(testUrl))
                .isInstanceOf(EmuBrowserException.class);

        // Verify
        verify(mockEmuBrowser, times(1)).retrieveContent();
        verify(mockEmuBrowser, times(1)).navigate(any(String.class));
        verify(mockHtmlParser, never()).parse(any(String.class), anyList());
    }

    @Test
    void if_ScrapePage_Fail_When_Navigate_Should_Throw_EmuBrowserException() throws Exception {
        // Given
        final String testUrl = "url";
        final SiteConfiguration siteConfiguration = createSiteConfiguration();
        final SelectorQuery selectorQuery = createScrapeQuery();
        siteConfiguration.setSelectorQueries(List.of(selectorQuery));
        siteConfiguration.setScrapingType(SiteConfiguration.DefaultScrapingType.STATIC);

        // When
        doThrow(EmuBrowserException.class).when(mockEmuBrowser).navigate(testUrl);

        // Then
        assertThatThrownBy(() -> dynamicScrapingBotUnderTest.scrapePage(testUrl))
                .isInstanceOf(EmuBrowserException.class);

        // Verify
        verify(mockEmuBrowser, times(1)).navigate(any(String.class));
        verify(mockHtmlParser, never()).parse(any(String.class), anyList());
        verify(mockEmuBrowser, never()).retrieveContent();

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
