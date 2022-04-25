package com.example.newsbackend.service.nlu.watson;

import com.ibm.cloud.sdk.core.http.Response;
import com.ibm.cloud.sdk.core.http.ServiceCall;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.natural_language_understanding.v1.model.AnalysisResults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class WatsonAnalyzeTest {

    private WatsonAnalyze mockWatsonAnalyzeUnderTest;


    @Mock
    private NaturalLanguageUnderstanding mockNaturalLanguageUnderstanding;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockWatsonAnalyzeUnderTest = new WatsonAnalyze(mockNaturalLanguageUnderstanding);
    }

    @Test
    void when_Analyze_Should_Return_AnalysisResults() throws Exception {
        // Given
        String text = "Rome wasn't built in a day";
        AnalysisResults expectResult = new AnalysisResults();

        WatsonAnalyzeOptions options = new WatsonAnalyzeOptions.Builder()
                .text(text)
                .build();
        ServiceCall<AnalysisResults> mockServiceCall = mock(ServiceCall.class);
        Response mockResponse = mock(Response.class);

        // When
        when(mockNaturalLanguageUnderstanding.analyze(any())).thenReturn(mockServiceCall);
        when(mockServiceCall.execute()).thenReturn(mockResponse);
        when(mockResponse.getResult()).thenReturn(expectResult);

        // Then
        AnalysisResults analyseResult = mockWatsonAnalyzeUnderTest.analyze(options);
        assertEquals(expectResult, analyseResult);
        verify(mockNaturalLanguageUnderstanding, times(1)).analyze(any());
        verify(mockServiceCall, times(1)).execute();
        verify(mockResponse, times(1)).getResult();
    }

}