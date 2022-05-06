package com.example.newsbackend.service.impl;

import com.example.newsbackend.service.impl.scrape.PageBody;
import com.example.newsbackend.entity.sites.RegisteredSite;
import com.example.newsbackend.entity.sites.SelectorQuery;
import com.example.newsbackend.entity.sites.SiteConfiguration;
import com.example.newsbackend.service.impl.ScrapeNewsPageServiceImpl;
import com.example.newsbackend.service.impl.scrape.stable.ParseValues;
import com.example.newsbackend.service.impl.serp.NewsResultPage;
import com.example.newsbackend.service.impl.PageScrapeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class ScrapeNewsPageServiceImplTest {


    @Mock
    private PageScrapeServiceImpl mockPageScrapeServiceImpl;

    private ScrapeNewsPageServiceImpl scrapeNewsPageServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        scrapeNewsPageServiceImplUnderTest = new ScrapeNewsPageServiceImpl(
                mockPageScrapeServiceImpl
        );
    }

    @Test
    void when_ScrapeNewsPage_Should_Return_List_Of_PageBody() throws Exception {


        //Given
        final NewsResultPage newsResultPage = new NewsResultPage();
        newsResultPage.setLink("https://test/test");
        newsResultPage.setTitle("testTitle");
        final RegisteredSite registeredSite = createRegisteredSite();
        final PageBody expectPageBody = new PageBody(
                newsResultPage,
                "content",
                registeredSite);

        final ParseValues parseValues = new ParseValues(Map.of("text","content"));

        final List<ParseValues> expectParseValues = List.of(parseValues);

        // When
        when(mockPageScrapeServiceImpl.scrape(any(SiteConfiguration.class),any(String.class))).thenReturn(expectParseValues);

        // Run the test
        final PageBody result = scrapeNewsPageServiceImplUnderTest.scrapeNewsPages(newsResultPage,registeredSite);

        // Verify the results
        assertThat(result).isNotNull();
        assertThat(result.getTextContent().trim()).isEqualTo(expectPageBody.getTextContent());


        verify(mockPageScrapeServiceImpl, times(1)).scrape(any(SiteConfiguration.class),any(String.class));


    }

    private RegisteredSite createRegisteredSite() {
        RegisteredSite registeredSite = new RegisteredSite();
        registeredSite.setId(0L);

        SiteConfiguration siteConfiguration = new SiteConfiguration();
        siteConfiguration.setId(0L);
        siteConfiguration.setCountry("siteCountry");
        siteConfiguration.setDescription("siteDescription");
        siteConfiguration.setDomain("siteDomain");
        siteConfiguration.setLanguage("siteLanguage");
        siteConfiguration.setLogo("siteLogo");
        siteConfiguration.setKeywords(List.of("siteKeywords"));
        siteConfiguration.setName("siteName");

        SelectorQuery selectorQuery = new SelectorQuery();
        selectorQuery.setId(0L);
        selectorQuery.setSelector("query");

        siteConfiguration.setSelectorQueries(List.of(selectorQuery));
        registeredSite.setSiteConfiguration(siteConfiguration);

        return registeredSite;
    }



}
