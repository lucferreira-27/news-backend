package com.example.newsbackend.service;

import com.example.newsbackend.repository.page.PageBody;
import com.example.newsbackend.repository.page.PageHeadline;
import com.example.newsbackend.repository.sites.RegisteredSite;
import com.example.newsbackend.repository.sites.SelectorQuery;
import com.example.newsbackend.repository.sites.SiteConfiguration;
import com.example.newsbackend.service.scrape.stable.ParseValues;
import com.example.newsbackend.service.tools.PageScrapeTool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class ScrapeNewsPageServiceImplTest {

    @Mock
    private PageValidator mockPageValidator;
    @Mock
    private PageScrapeTool mockPageScrapeTool;

    private ScrapeNewsPageServiceImpl scrapeNewsPageServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        scrapeNewsPageServiceImplUnderTest = new ScrapeNewsPageServiceImpl(
                mockPageValidator,
                mockPageScrapeTool
        );
    }

    @Test
    void when_ScrapeNewsPage_Should_Return_List_Of_PageBody() throws Exception {


        //Given
        final PageHeadline headline = new PageHeadline(
                "headline",
                "title");
        final PageBody expectPageBody = new PageBody(
                headline,
                "content");

        final ParseValues parseValues = new ParseValues(List.of(expectPageBody.getTextContent()));

        final RegisteredSite registeredSite = createRegisteredSite();
        final List<ParseValues> expectParseValues = List.of(parseValues);

        // When
        when(mockPageValidator.validatePage(any(String.class))).thenReturn(registeredSite);
        when(mockPageScrapeTool.scrape(any(SiteConfiguration.class),any(String.class))).thenReturn(expectParseValues);

        // Run the test
        final List<PageBody> result = scrapeNewsPageServiceImplUnderTest.scrapeNewsPages(List.of(headline));

        // Verify the results
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getPageHeadline()).isEqualTo(expectPageBody.getPageHeadline());
        assertThat(result.get(0).getTextContent()).isEqualTo(expectPageBody.getTextContent());


        verify(mockPageValidator, times(1)).validatePage(headline.getUrl());
        verify(mockPageScrapeTool, times(1)).scrape(any(SiteConfiguration.class),any(String.class));


    }

    private RegisteredSite createRegisteredSite() {
        RegisteredSite registeredSite = new RegisteredSite();
        registeredSite.setId(0L);

        SiteConfiguration siteConfiguration = new SiteConfiguration();
        siteConfiguration.setId(0L);
        siteConfiguration.setSiteCountry("siteCountry");
        siteConfiguration.setSiteDescription("siteDescription");
        siteConfiguration.setSiteDomain("siteDomain");
        siteConfiguration.setSiteLanguage("siteLanguage");
        siteConfiguration.setSiteLogo("siteLogo");
        siteConfiguration.setSiteKeywords("siteKeywords");
        siteConfiguration.setSiteName("siteName");

        SelectorQuery selectorQuery = new SelectorQuery();
        selectorQuery.setId(0L);
        selectorQuery.setSelector("query");

        siteConfiguration.setScrapeQueries(List.of(selectorQuery));
        registeredSite.setSiteConfiguration(siteConfiguration);

        return registeredSite;
    }

    @Test
    void if_Page_Is_Not_Valid_Should_Throw_PageValidatorException() throws Exception {

        //Given
        final PageHeadline headline = new PageHeadline(
                "headline",
                "title");

        // When
        when(mockPageValidator.validatePage(any(String.class))).thenThrow(PageValidatorException.class);

        // Run the test
        assertThatThrownBy(() -> scrapeNewsPageServiceImplUnderTest.scrapeNewsPages(List.of(headline)))
                .isInstanceOf(PageValidatorException.class);

        // Verify the results
        verify(mockPageValidator, times(1)).validatePage(headline.getUrl());
        verify(mockPageScrapeTool, never()).scrape(any(SiteConfiguration.class),any(String.class));

    }

}
