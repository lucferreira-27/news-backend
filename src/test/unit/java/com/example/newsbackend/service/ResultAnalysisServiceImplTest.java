package com.example.newsbackend.service;

import com.example.newsbackend.repository.storage.SearchInfoResult;
import com.example.newsbackend.repository.storage.StorageResult;
import com.example.newsbackend.repository.storage.StorageResultRepository;
import com.example.newsbackend.repository.storage.StorageStatus;
import com.example.newsbackend.repository.page.PageBody;
import com.example.newsbackend.repository.sites.RegisteredSite;
import com.example.newsbackend.repository.storage.analise.ContentAnaliseResult;
import com.example.newsbackend.repository.storage.analise.TextConcept;
import com.example.newsbackend.repository.storage.analise.TextEntity;
import com.example.newsbackend.repository.storage.analise.TextKeyword;
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
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasValue;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ResultAnalysisServiceImplTest {

    private ResultAnalysisServiceImpl resultAnalysisServiceUnderTest;

    @Mock
    private ParseNewsServiceImpl mockParseNewsService;
    @Mock
    private WatsonNLUService mockWatsonNLUService;
    @Mock
    private StorageResultRepository mockStorageResultRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        resultAnalysisServiceUnderTest = new ResultAnalysisServiceImpl(
                mockParseNewsService,
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
        String pageBodyContent = "Random useless text";
        final RegisteredSite registeredSite = new RegisteredSite();
        PageBody pageBody = new PageBody(newsResultPage,pageBodyContent,registeredSite);
        //When
        when(mockStorageResultRepository.save(any(StorageResult.class))).thenReturn(new StorageResult());
        when(mockParseNewsService.getNewsPageBody(newsResultPage,registeredSite)).thenReturn(pageBody);
        when(mockParseNewsService.validateNewsSite(anyString())).thenReturn(registeredSite);
        when(mockWatsonNLUService.startAnalysis(pageBodyContent)).thenReturn(jsonResponseExpected);

        //Then
        StorageResult storageResult = resultAnalysisServiceUnderTest.analysis(newsResultPage);

        //Verify
        assertThat(storageResult).isNotNull();
        assertThat(storageResult.getAnalysisTime()).isGreaterThan(0);
        assertThat(storageResult.getContentAnaliseResult()).isNotNull();
        assertThat(storageResult.getRegisteredSite()).isNotNull();
        assertThat(storageResult.getRegisteredSite()).isEqualTo(registeredSite);
        ContentAnaliseResult contentAnaliseResult = storageResult.getContentAnaliseResult();

        assertThat(contentAnaliseResult.getLanguage()).isEqualTo("pt");
        assertThat(contentAnaliseResult.getSentiment().getLabel()).isEqualTo("negative");
        assertThat(contentAnaliseResult.getSentiment().getScore()).isEqualTo(-0.726577);

        List<TextConcept> concepts = contentAnaliseResult.getConcepts();
        List<TextEntity> entities = contentAnaliseResult.getEntities();
        List<TextKeyword> keywords = contentAnaliseResult.getKeywords();

        assertThat(concepts.size()).isEqualTo(3);
        assertThat(entities.size()).isEqualTo(3);
        assertThat(keywords.size()).isEqualTo(3);

        assertThat(concepts.get(0).getText()).isEqualTo("Supremo Tribunal Federal");
        assertThat(concepts.get(0).getRelevance()).isEqualTo(0.991806);

        assertThat(entities.get(0).getText()).isEqualTo("TSE");
        assertThat(entities.get(0).getRelevance()).isEqualTo(0.95276);
        assertThat(entities.get(0).getType()).isEqualTo("Organization");
        assertThat(entities.get(0).getConfidence()).isEqualTo(1);
        assertThat(entities.get(0).getCount()).isEqualTo(12);
        assertThat(entities.get(0).getSentimentScore()).isEqualTo(0);

        assertThat(keywords.get(0).getText()).isEqualTo("Ministros do TSE");
        assertThat(keywords.get(0).getRelevance()).isEqualTo(0.72493);
        assertThat(keywords.get(0).getCount()).isEqualTo(1);

        assertThat(storageResult.getStatusMessage()).isEqualTo("OK");
        assertThat(storageResult.getStatus()).isEqualTo(StorageStatus.SUCCESS);

        verify(mockStorageResultRepository, times(1)).save(any(StorageResult.class));
        verify(mockParseNewsService, times(1)).getNewsPageBody(newsResultPage,registeredSite);
        verify(mockParseNewsService, times(1)).validateNewsSite(anyString());
        verify(mockWatsonNLUService, times(1)).startAnalysis(pageBodyContent);

    }
    @Test
    void when_Analyze_Should_Reuse_StorageResult_If_Found_Given_Url() throws Exception {
        //Given
        final NewsResultPage newsResultPage = new NewsResultPage();
        newsResultPage.setTitle("title");
        newsResultPage.setLink("link");
        StorageResult expectStorageResult = new StorageResult();
        //When
        when(mockStorageResultRepository.findBySearchInfoUrl(anyString())).thenReturn(Optional.of(expectStorageResult));


        //Then
        StorageResult result = resultAnalysisServiceUnderTest.analysis(newsResultPage);

        //Verify
        assertEquals(expectStorageResult, result);


        verify(mockStorageResultRepository, never()).save(any(StorageResult.class));
        verify(mockStorageResultRepository, times(1)).findBySearchInfoUrl(anyString());

        verify(mockParseNewsService, never()).getNewsPageBody(any(NewsResultPage.class),any(RegisteredSite.class));
        verify(mockParseNewsService, never()).validateNewsSite(anyString());
        verify(mockWatsonNLUService, never()).startAnalysis(anyString());


    }
    @Test
    void if_Scrape_Failed_StorageStatus_Should_Be_Invalid_Query() throws Exception {
        //Given
        final NewsResultPage newsResultPage = new NewsResultPage();
        newsResultPage.setTitle("title");
        newsResultPage.setLink("link");
        newsResultPage.setSnippet("snippet");
        String pageBodyContent = "Random useless text";
        final RegisteredSite registeredSite = new RegisteredSite();

        //When
        when(mockParseNewsService.validateNewsSite(anyString())).thenReturn(registeredSite);

        doThrow(new ScrapingException("Error"))
                .when(mockParseNewsService).getNewsPageBody(newsResultPage,registeredSite);

        //Then
        StorageResult storageResult = resultAnalysisServiceUnderTest.analysis(newsResultPage);

        //Verify
        assertThat(storageResult).isNotNull();
        assertThat(storageResult.getAnalysisTime()).isGreaterThan(0);
        assertThat(storageResult.getContentAnaliseResult()).isNull();
        assertThat(storageResult.getRegisteredSite()).isNotNull();
        assertThat(storageResult.getStatusMessage()).isEqualTo("Scraping failed because query proved was invalid");
        assertThat(storageResult.getStatus()).isEqualTo(StorageStatus.INVALID_QUERY);

        assertThat(storageResult.getSearchInfo()).isNotNull();

        SearchInfoResult searchInfoResult = storageResult.getSearchInfo();
        assertThat(searchInfoResult.getHeadline()).isEqualTo(newsResultPage.getTitle());
        assertThat(searchInfoResult.getUrl()).isEqualTo(newsResultPage.getLink());
        assertThat(searchInfoResult.getSnippet()).isEqualTo(newsResultPage.getSnippet());

        verify(mockStorageResultRepository, times(1)).save(any(StorageResult.class));
        verify(mockParseNewsService, times(1)).validateNewsSite(anyString());
        verify(mockParseNewsService, times(1))
                .getNewsPageBody(newsResultPage,registeredSite);
        verify(mockWatsonNLUService, never()).startAnalysis(pageBodyContent);

    }
    @Test
    void if_PageValidate_Failed_StorageStatus_Should_Be_Not_Found() throws Exception {
        //Given
        final NewsResultPage newsResultPage = new NewsResultPage();
        newsResultPage.setTitle("title");
        newsResultPage.setLink("link");
        newsResultPage.setSnippet("snippet");
        String pageBodyContent = "Random useless text";
        final RegisteredSite registeredSite = new RegisteredSite();

        //When
        when(mockParseNewsService.validateNewsSite(anyString())).thenReturn(registeredSite);

        doThrow(new PageValidatorException("Error"))
                .when(mockParseNewsService).validateNewsSite(anyString());

        //Then
        StorageResult storageResult = resultAnalysisServiceUnderTest.analysis(newsResultPage);


        //Verify
        assertThat(storageResult).isNotNull();
        assertThat(storageResult.getAnalysisTime()).isGreaterThan(0);
        assertThat(storageResult.getContentAnaliseResult()).isNull();
        assertThat(storageResult.getRegisteredSite()).isNull();
        assertThat(storageResult.getStatusMessage()).isEqualTo("Page site isn't registered in database");
        assertThat(storageResult.getStatus()).isEqualTo(StorageStatus.NOT_FOUND);

        assertThat(storageResult.getSearchInfo()).isNotNull();

        SearchInfoResult searchInfoResult = storageResult.getSearchInfo();
        assertThat(searchInfoResult.getHeadline()).isEqualTo(newsResultPage.getTitle());
        assertThat(searchInfoResult.getUrl()).isEqualTo(newsResultPage.getLink());
        assertThat(searchInfoResult.getSnippet()).isEqualTo(newsResultPage.getSnippet());

        verify(mockStorageResultRepository, times(1)).save(any(StorageResult.class));
        verify(mockParseNewsService, times(1)).validateNewsSite(anyString());
        verify(mockParseNewsService, never())
                .getNewsPageBody(newsResultPage,registeredSite);
        verify(mockWatsonNLUService, never()).startAnalysis(pageBodyContent);

    }
    @Test
    void if_Unexpect_Exception_StorageStatus_Should_Be_Internal_Server_Error() throws Exception {
        //Given
        final NewsResultPage mockNewsResultPage = mock(NewsResultPage.class);
        mockNewsResultPage.setTitle("title");
        mockNewsResultPage.setLink("link");
        mockNewsResultPage.setSnippet("snippet");
        String pageBodyContent = "Random useless text";
        final RegisteredSite registeredSite = new RegisteredSite();

        //When

        when(mockNewsResultPage.getLink())
                .thenReturn("link")
                .thenThrow(new RuntimeException("Error"))
                .thenReturn("link");
        when(mockNewsResultPage.getTitle())
                .thenReturn("title");
        when(mockNewsResultPage.getSnippet())
                .thenReturn("snippet");
        //Then

        StorageResult storageResult = resultAnalysisServiceUnderTest.analysis(mockNewsResultPage);


        //Verify
        assertThat(storageResult).isNotNull();
        assertThat(storageResult.getAnalysisTime()).isGreaterThan(0);
        assertThat(storageResult.getContentAnaliseResult()).isNull();
        assertThat(storageResult.getRegisteredSite()).isNull();
        assertThat(storageResult.getStatus()).isEqualTo(StorageStatus.INTERNAL_SERVER_ERROR);
        assertThat(storageResult.getStatusMessage()).isEqualTo("Internal Server Error");

        assertThat(storageResult.getSearchInfo()).isNotNull();

        SearchInfoResult searchInfoResult = storageResult.getSearchInfo();
        assertThat(searchInfoResult.getHeadline()).isEqualTo("title");
        assertThat(searchInfoResult.getUrl()).isEqualTo("link");
        assertThat(searchInfoResult.getSnippet()).isEqualTo("snippet");

        verify(mockStorageResultRepository, times(1)).save(any(StorageResult.class));
        verify(mockParseNewsService, never()).validateNewsSite(anyString());
        verify(mockParseNewsService, never())
                .getNewsPageBody(mockNewsResultPage,registeredSite);
        verify(mockWatsonNLUService, never()).startAnalysis(pageBodyContent);

    }
    private String readTextFromFile(String name) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(name).getFile());
        String result = Files.readString(Paths.get(file.getAbsolutePath()));
        return result;
    }
}