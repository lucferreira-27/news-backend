package com.example.newsbackend.service.impl.scrape.stable;

import com.example.newsbackend.entity.sites.SelectorQuery;
import com.example.newsbackend.entity.sites.SiteConfiguration;
import com.example.newsbackend.exception.ScrapingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StaticScrapingBotTest {


    @Mock
    private ContentExtractImpl mockContentExtractImpl;

    @Mock
    private HTMLParserImpl mockHtmlParser;

    private StaticScrapingBot staticScrapingBotUnderTest;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        staticScrapingBotUnderTest = new StaticScrapingBot(mockHtmlParser, mockContentExtractImpl);
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
        siteConfiguration.setScrapingType(SiteConfiguration.ScrapingType.DYNAMIC);

        StaticScrapingBot spyStaticScrapingBot = spy(staticScrapingBotUnderTest);

        // When
        doReturn(testContentExtract).when(spyStaticScrapingBot).scrapePage(testUrl);
        doReturn(expectParseValues).when(mockHtmlParser).parse(testContentExtract, scrapeQueries);

        // Then
        final List<ParseValues> result = spyStaticScrapingBot.extractPageContents(testUrl, siteConfiguration);

        // Verify
        assertThat(result).isEqualTo(expectParseValues);
        verify(mockHtmlParser, times(1)).parse(any(String.class), anyList());
        verify(spyStaticScrapingBot, times(1)).scrapePage(any(String.class));
        verifyNoMoreInteractions(mockHtmlParser);


    }

    @Test
    void if_ScrapePage_Fail_Should_Throw_ScrapingException() throws Exception {
        // Given
        final String testUrl = "url";
        final SiteConfiguration siteConfiguration = createSiteConfiguration();
        final SelectorQuery selectorQuery = createScrapeQuery();
        final String exceptionMsg = "Error while getting page contents";
        siteConfiguration.setSelectorQueries(List.of(selectorQuery));
        siteConfiguration.setScrapingType(SiteConfiguration.ScrapingType.DYNAMIC);
        StaticScrapingBot spyStaticScrapingBot = spy(staticScrapingBotUnderTest);

        // When
        doThrow(IOException.class).when(spyStaticScrapingBot).scrapePage(testUrl);

        // Then
        assertThatThrownBy(() -> spyStaticScrapingBot.extractPageContents(testUrl, siteConfiguration))
                .isInstanceOf(ScrapingException.class).hasMessage(exceptionMsg);

        // Verify
        verify(spyStaticScrapingBot, times(1)).scrapePage(any(String.class));
        verify(mockHtmlParser, never()).parse(any(String.class), anyList());
    }

    @Test
    void when_ScrapePage_Should_Return_Extracted_Content() throws IOException {
        // Given
        final String testUrl = "url";
        final SiteConfiguration siteConfiguration = createSiteConfiguration();
        final SelectorQuery selectorQuery = createScrapeQuery();
        final String testContentExtract = "contentExtract";
        siteConfiguration.setSelectorQueries(List.of(selectorQuery));
        siteConfiguration.setScrapingType(SiteConfiguration.ScrapingType.STATIC);

        // When
        when(mockContentExtractImpl.extract(testUrl)).thenReturn(testContentExtract);

        // Then
        final String result = staticScrapingBotUnderTest.scrapePage(testUrl);

        // Verify
        assertThat(result).isEqualTo(testContentExtract);
        verify(mockContentExtractImpl, times(1)).extract(any(String.class));

    }

    @Test
    void if_ScrapePage_Fail_Should_Throw_IOException() throws IOException {
        // Given
        final String testUrl = "url";
        final SiteConfiguration siteConfiguration = createSiteConfiguration();
        final SelectorQuery selectorQuery = createScrapeQuery();
        siteConfiguration.setSelectorQueries(List.of(selectorQuery));
        siteConfiguration.setScrapingType(SiteConfiguration.ScrapingType.STATIC);

        // When
        when(mockContentExtractImpl.extract(testUrl)).thenThrow(IOException.class);

        // Then
        assertThatThrownBy(() -> staticScrapingBotUnderTest.scrapePage(testUrl))
                .isInstanceOf(IOException.class);

        // Verify
        verify(mockContentExtractImpl, times(1)).extract(any(String.class));
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
