package com.example.newsbackend.service.impl.nlu.watson;

import com.ibm.cloud.sdk.core.http.Response;
import com.ibm.cloud.sdk.core.http.ServiceCall;
import com.ibm.watson.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.natural_language_understanding.v1.model.AnalysisResults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class WatsonAnalyzeImplTest {

    private WatsonAnalyzeImpl mockWatsonAnalyzeImplUnderTest;


    @Mock
    private NaturalLanguageUnderstanding mockNaturalLanguageUnderstanding;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockWatsonAnalyzeImplUnderTest = new WatsonAnalyzeImpl(mockNaturalLanguageUnderstanding);
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
        AnalysisResults analyseResult = mockWatsonAnalyzeImplUnderTest.analyze(options);
        assertEquals(expectResult, analyseResult);
        verify(mockNaturalLanguageUnderstanding, times(1)).analyze(any());
        verify(mockServiceCall, times(1)).execute();
        verify(mockResponse, times(1)).getResult();
    }

}