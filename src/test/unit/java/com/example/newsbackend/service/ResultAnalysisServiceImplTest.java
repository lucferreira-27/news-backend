package com.example.newsbackend.service;

import com.example.newsbackend.repository.history.StorageResult;
import com.example.newsbackend.repository.history.StorageResultRepository;
import com.example.newsbackend.repository.history.StorageStatus;
import com.example.newsbackend.repository.page.PageBody;
import com.example.newsbackend.repository.sites.RegisteredSite;
import com.example.newsbackend.service.nlu.watson.WatsonNLUService;
import com.example.newsbackend.service.scrape.ScrapingException;
import com.example.newsbackend.service.serp.NewsResultPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class ResultAnalysisServiceImplTest {

    private ResultAnalysisServiceImpl resultAnalysisServiceUnderTest;

    @Mock
    private ScrapeNewsPageServiceImpl mockScrapeNewsPageService;
    @Mock
    private WatsonNLUService mockWatsonNLUService;
    @Mock
    private StorageResultRepository mockStorageResultRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        resultAnalysisServiceUnderTest = new ResultAnalysisServiceImpl(
                mockScrapeNewsPageService,
                mockWatsonNLUService,
                mockStorageResultRepository);
    }

    @Test
    void when_Analyze_Should_Return_StorageResult() throws Exception {
        //Given
        NewsResultPage newsResultPage = new NewsResultPage();
        newsResultPage.setTitle("title");
        newsResultPage.setLink("link");
        String jsonResponseExpected = readTextFromFile("test_watson_nlu_response.json");
        String pageBodyContent = "Roma is the capital of Italy, and the country's largest city.";
        final RegisteredSite registeredSite = new RegisteredSite();
        PageBody pageBody = new PageBody(newsResultPage,pageBodyContent,registeredSite);
        //When
        when(mockStorageResultRepository.save(any(StorageResult.class))).thenReturn(new StorageResult());
        when(mockScrapeNewsPageService.scrapeNewsPages(newsResultPage)).thenReturn(pageBody);
        when(mockWatsonNLUService.startAnalysis(pageBodyContent)).thenReturn(jsonResponseExpected);

        //Then
        StorageResult storageResult = resultAnalysisServiceUnderTest.analysis(newsResultPage);
        assertThat(storageResult).isNotNull();
        assertThat(storageResult.getAnalysisTime()).isGreaterThan(0);
        assertThat(storageResult.getContentAnaliseResult().toString()).isEqualTo(jsonResponseExpected);
        assertThat(storageResult.getStatusMessage()).isEqualTo("OK");
        assertThat(storageResult.getStatus()).isEqualTo(StorageStatus.SUCCESS);

    }
    private String readTextFromFile(String name) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(name).getFile());
        String result = Files.readString(Paths.get(file.getAbsolutePath()));
        return result;
    }
}