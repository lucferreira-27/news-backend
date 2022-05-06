package com.example.newsbackend.controller;

import com.example.newsbackend.NewsBackendApplication;
import com.example.newsbackend.entity.search.StorageResult;
import com.example.newsbackend.exception.ScaleAPIException;
import com.example.newsbackend.entity.search.SearchHistory;
import com.example.newsbackend.repository.storage.SearchHistoryRepository;
import com.example.newsbackend.service.impl.serp.*;
import com.example.newsbackend.util.URLCustom;
import com.ibm.cloud.sdk.core.http.Response;
import com.ibm.cloud.sdk.core.http.ServiceCall;
import com.ibm.watson.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.natural_language_understanding.v1.model.AnalysisResults;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = NewsBackendApplication.class)
@AutoConfigureMockMvc
class RestControllerSearchTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private HTTPRequestServiceImpl mockHttpRequestServiceImpl;
    @MockBean
    private NaturalLanguageUnderstanding mockNlu;
    @SpyBean
    private ScaleAPIRequestServiceImpl spyScaleAPIRequestServiceImpl;

    @SpyBean
    private URLCustom mockUrlCustom;
    @SpyBean
    private SearchHistoryRepository spySearchHistoryRepository;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
        spySearchHistoryRepository.deleteAll();
    }

    @Test
    @Transactional
    void should_Return_As_Response_StatusOK_With_SearchHistory() throws Exception {

        //Given
        String thirdPartyScaleResponse = readTextFromFile("test_int_scale_serp_success.json");
        String thirdPartyWatsonResponse = readTextFromFile("test_int_watson_nlu_success.json");
        ServiceCall<AnalysisResults> mockServiceCall = mock(ServiceCall.class);
        Response mockResponse = mock(Response.class);
        AnalysisResults mockAnalysisResults = mock(AnalysisResults.class);

        // When
        when(mockHttpRequestServiceImpl.sendRequest(any(URL.class))).thenReturn(thirdPartyScaleResponse);
        when(mockNlu.analyze(any())).thenReturn(mockServiceCall);
        when(mockServiceCall.execute()).thenReturn(mockResponse);
        when(mockResponse.getResult()).thenReturn(mockAnalysisResults);
        when(mockAnalysisResults.toString()).thenReturn(thirdPartyWatsonResponse);
        // Then
        var resultActions = mvc.perform(get("/search")
                .param("q", "bolsonaro")
                .accept("application/json"));
        checkJsonPathSuccessReturn(resultActions.andExpect(status().isOk()));
        // Verify
        verify(mockHttpRequestServiceImpl, times(1)).sendRequest(any(URL.class));
        verify(mockNlu, times(1)).analyze(any());
        verify(mockUrlCustom, times(1)).buildParametersURL(anyString(), any());


    }

    @Test
    void should_Return_As_Response_StatusOK_With_SearchHistory_Given_Parameters() throws Exception {

        //Given
        String thirdPartyScaleResponse = readTextFromFile("test_int_scale_serp_success.json");
        String thirdPartyWatsonResponse = readTextFromFile("test_int_watson_nlu_success.json");
        ServiceCall<AnalysisResults> mockServiceCall = mock(ServiceCall.class);
        Response mockResponse = mock(Response.class);
        AnalysisResults mockAnalysisResults = mock(AnalysisResults.class);

        // When
        when(mockHttpRequestServiceImpl.sendRequest(any(URL.class))).thenReturn(thirdPartyScaleResponse);
        when(mockNlu.analyze(any())).thenReturn(mockServiceCall);
        when(mockServiceCall.execute()).thenReturn(mockResponse);
        when(mockResponse.getResult()).thenReturn(mockAnalysisResults);
        when(mockAnalysisResults.toString()).thenReturn(thirdPartyWatsonResponse);
        // Then
        var resultActions = mvc.perform(get("/search")
                .param("q", "bolsonaro")
                .param("lang", "pt")
                .param("sortBy", "relevance")
                .param("page", "1")
                .param("country", "br")
                .param("location", "State of Sao Paulo")
                .accept("application/json"));
        checkJsonPathSuccessReturn(resultActions.andExpect(status().isOk()));
        // Verify
        verify(mockHttpRequestServiceImpl, times(1)).sendRequest(any(URL.class));
        verify(mockNlu, times(1)).analyze(any());

    }

    @Test
    void should_Return_As_Response_StatusBadRequest_Request_When_No_Results_Found() throws Exception {

        //Given
        String thirdPartyScaleResponse = readTextFromFile("test_int_scale_serp_none_result.json");


        // When
        when(mockHttpRequestServiceImpl.sendRequest(any(URL.class))).thenReturn(thirdPartyScaleResponse);

        // Then
        var resultActions = mvc.perform(get("/search")
                .param("q", "bolsonaro")
                .accept("application/json"));
        resultActions.andExpect(status().isBadRequest());
        // Verify
        verify(mockHttpRequestServiceImpl, times(1)).sendRequest(any(URL.class));


    }
    @Test
    void should_Return_As_Response_Status_FailedDependency_Request_When_ScaleAPIException_Is_Throw() throws Exception {

        // When
        doThrow(ScaleAPIException.class).when(spyScaleAPIRequestServiceImpl).getResponse(any(RequestParameters.class));

        // Then
        var resultActions = mvc.perform(get("/search")
                .param("q", "bolsonaro")
                .accept("application/json"));
        resultActions.andExpect(status().isBadRequest());


    }
    @Test
    void when_Find_Should_Return_As_Response_StatusOK_With_SearchHistory_Given_Id() throws Exception {
        //Given
        SearchHistory searchHistory = new SearchHistory();
        StorageResult storage = new StorageResult();
        storage.setSearchHistory(searchHistory);
        StorageResult storage2 = new StorageResult();
        storage2.setSearchHistory(searchHistory);
        StorageResult storage3 = new StorageResult();
        storage3.setSearchHistory(searchHistory);
        searchHistory.getStorageResults().addAll(List.of(storage,storage2,storage3));
        spySearchHistoryRepository.save(searchHistory);
        Long id = spySearchHistoryRepository.findAll().get(0).getId();
        //Then
        mvc.perform(get("/search/history/find/" + id)
                .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(id.intValue())))
                .andExpect(jsonPath("$.storageResults", Matchers.hasSize(3)));
        //Verify
        verify(spySearchHistoryRepository, times(1)).findById(id);

    }
    @Test
    void when_Find_Should_Return_As_Response_StatusNotFound_Given_Id() throws Exception {

        //Then
        mvc.perform(get("/search/history/find/1")
                        .accept("application/json"))
                .andExpect(status().isNotFound());

        //Verify
        verify(spySearchHistoryRepository, times(1)).findById(1L);

    }
    @Test
    void when_Find_All_should_Return_As_Response_StatusOK_With_SearchHistory_Given_Id() throws Exception {

        //Given
        spySearchHistoryRepository.saveAll(List.of(new SearchHistory(),new SearchHistory(),new SearchHistory()));

        //Then
        mvc.perform(get("/search/history/find/all")
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(3)));
        //Verify
        verify(spySearchHistoryRepository, times(1)).findAll();

    }
    @Test
    void when_Delete_should_Return_As_Response_StatusNotFound_Given_Id() throws Exception {


        //Then
        mvc.perform(delete("/search/history/delete/1")
                        .accept("application/json"))
                .andExpect(status().isNotFound());
        //Verify
        verify(spySearchHistoryRepository, times(1)).existsById(1L);
        verify(spySearchHistoryRepository, never()).deleteById(1L);

    }
    @Test
    void when_Delete_should_Return_As_Response_StatusAccepted_Given_Id() throws Exception {

        //Given
        SearchHistory searchHistory = new SearchHistory();
        spySearchHistoryRepository.save(searchHistory);
        Long id = spySearchHistoryRepository.findAll().get(0).getId();
        //Then
        mvc.perform(delete("/search/history/delete/"+id)
                        .accept("application/json"))
                .andExpect(status().isAccepted());
        //Verify
        verify(spySearchHistoryRepository, times(1)).deleteById(id);
        assertThat(spySearchHistoryRepository.existsById(id)).isFalse();

    }
    @Test
    void when_Delete_All_should_Return_As_Response_StatusAccepted() throws Exception {
        //Given
        spySearchHistoryRepository.saveAll(List.of(new SearchHistory(),new SearchHistory(),new SearchHistory()));

        //Then
        mvc.perform(delete("/search/history/delete/all")
                        .accept("application/json"))
                .andExpect(status().isAccepted());
        //Verify
        assertThat(spySearchHistoryRepository.findAll().size()).isEqualTo(0);

    }


    private String readTextFromFile(String name) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(name).getFile());
        String result = Files.readString(Paths.get(file.getAbsolutePath()));
        return result;
    }
    private void checkJsonPathSuccessReturn(ResultActions resultActions) throws Exception {

        String expectedDate = LocalDateTime.now().toString()
                .substring(0, 10);

        resultActions.andExpect(jsonPath("$.id",
                Matchers.greaterThan(0)))
                .andExpect(jsonPath("$.date",
                        Matchers.containsString(expectedDate)))
                .andExpect(jsonPath("$.searchTerm",
                        Matchers.is("bolsonaro")))
                .andExpect(jsonPath("$.storageResults.length()",
                        Matchers.is(1)))
                .andExpect(jsonPath("$.storageResults[0].id",
                        Matchers.greaterThan(0)))
                .andExpect(jsonPath("$.storageResults[0].registeredSite.id",
                        Matchers.greaterThan(0)))
                .andExpect(jsonPath("$.storageResults[0].registeredSite.url",
                        Matchers.is("https://g1.globo.com/")))
                .andExpect(jsonPath("$.storageResults[0].registeredSite.siteConfiguration.id",
                        Matchers.greaterThan(0)))
                .andExpect(jsonPath("$.storageResults[0].registeredSite.siteConfiguration.name",
                        Matchers.is("g1")))
                .andExpect(jsonPath("$.storageResults[0].registeredSite.siteConfiguration.description",
                        Matchers.is("")))
                .andExpect(jsonPath("$.storageResults[0].registeredSite.siteConfiguration.logo",
                        Matchers.is("")))
                .andExpect(jsonPath("$.storageResults[0].registeredSite.siteConfiguration.domain",
                        Matchers.is("https://g1.globo.com/")))
                .andExpect(jsonPath("$.storageResults[0].registeredSite.siteConfiguration.language",
                        Matchers.is("pt-br")))
                .andExpect(jsonPath("$.storageResults[0].registeredSite.siteConfiguration.country",
                        Matchers.is("Brazil")))
                .andExpect(jsonPath("$.storageResults[0].registeredSite.siteConfiguration.language",
                        Matchers.is("pt-br")))
                .andExpect(jsonPath("$.storageResults[0].registeredSite.siteConfiguration.scrapingType",
                        Matchers.is("STATIC")))
                .andExpect(jsonPath("$.storageResults[0].registeredSite.siteConfiguration.selectorQueries[0].id",
                        Matchers.greaterThan(0)))
                .andExpect(jsonPath("$.storageResults[0].registeredSite.siteConfiguration.selectorQueries[0].selector",
                        Matchers.is(".mc-column.content-text")))
                .andExpect(jsonPath("$.storageResults[0].registeredSite.siteConfiguration.selectorQueries[0].attribute",
                        Matchers.is("text")))
                .andExpect(jsonPath("$.storageResults[0].status",
                        Matchers.is("SUCCESS")))
                .andExpect(jsonPath("$.storageResults[0].statusMessage",
                        Matchers.is("OK")))
                .andExpect(jsonPath("$.storageResults[0].analysisTime",
                        Matchers.is(Matchers.greaterThan(100))))
                .andExpect(jsonPath("$.storageResults[0].contentAnaliseResult.id",
                        Matchers.greaterThan(0)))
                .andExpect(jsonPath("$.storageResults[0].contentAnaliseResult.language",
                        Matchers.is("pt")))
                .andExpect(jsonPath("$.storageResults[0].contentAnaliseResult.concepts[0].id",
                        Matchers.greaterThan(0)))
                .andExpect(jsonPath("$.storageResults[0].contentAnaliseResult.concepts[0].text",
                        Matchers.is("Imposto")))
                .andExpect(jsonPath("$.storageResults[0].contentAnaliseResult.concepts[0].relevance",
                        Matchers.is(0.994197)))
                .andExpect(jsonPath("$.storageResults[0].contentAnaliseResult.entities[0].id",
                        Matchers.greaterThan(0)))
                .andExpect(jsonPath("$.storageResults[0].contentAnaliseResult.entities[0].text",
                        Matchers.is("Bolsonaro")))
                .andExpect(jsonPath("$.storageResults[0].contentAnaliseResult.entities[0].relevance",
                        Matchers.is(0.952258)))
                .andExpect(jsonPath("$.storageResults[0].contentAnaliseResult.entities[0].type",
                        Matchers.is("Person")))
                .andExpect(jsonPath("$.storageResults[0].contentAnaliseResult.entities[0].count",
                        Matchers.is(4)))
                .andExpect(jsonPath("$.storageResults[0].contentAnaliseResult.entities[0].confidence",
                        Matchers.is(1.0)))
                .andExpect(jsonPath("$.storageResults[0].contentAnaliseResult.entities[0].sentimentScore",
                        Matchers.is(0.0)))
                .andExpect(jsonPath("$.storageResults[0].contentAnaliseResult.keywords[0].id",
                        Matchers.greaterThan(0)))
                .andExpect(jsonPath("$.storageResults[0].contentAnaliseResult.keywords[0].text",
                        Matchers.is("presidente Jair Bolsonaro")))
                .andExpect(jsonPath("$.storageResults[0].contentAnaliseResult.keywords[0].relevance",
                        Matchers.is(0.702258)))
                .andExpect(jsonPath("$.storageResults[0].contentAnaliseResult.keywords[0].count",
                        Matchers.is(1)))
                .andExpect(jsonPath("$.storageResults[0].contentAnaliseResult.sentiment.label",
                        Matchers.is("negative")))
                .andExpect(jsonPath("$.storageResults[0].contentAnaliseResult.sentiment.score",
                        Matchers.is(-0.373511)))
                .andExpect(jsonPath("$.storageResults[0].searchInfo.headline",
                        Matchers.is("Pacote de 'bondades' de Bolsonaro por reeleição obriga quem estiver no poder em 2023 a fazer ajuste fiscal")))
                .andExpect(jsonPath("$.storageResults[0].searchInfo.url",
                        Matchers.is("https://g1.globo.com/politica/blog/valdo-cruz/post/2022/04/19/pacote-de-bondades-de-bolsonaro-por-reeleicao-obriga-quem-estiver-no-poder-em-2023-a-fazer-ajuste-fiscal.ghtml")))
                .andExpect(jsonPath("$.storageResults[0].searchInfo.snippet",
                        Matchers.is("A série de medidas do governo conhecida como pacote de \"bondades\", adotada pelo presidente Jair Bolsonaro em busca da reeleição,...")))
                .andReturn();

    }
}