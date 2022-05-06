package com.example.newsbackend.service.impl.nlu.watson;

import com.example.newsbackend.exception.NLUException;
import com.ibm.watson.natural_language_understanding.v1.model.AnalysisResults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class WatsonNLUServiceImplTest {

    private WatsonNLUServiceImpl watsonNLUServiceImplUnderTest;

    @Mock
    private WatsonAnalyzeImpl mockWatsonAnalyzeImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        watsonNLUServiceImplUnderTest = new WatsonNLUServiceImpl(mockWatsonAnalyzeImpl);

    }


    @Test
    void when_StartAnalysis_Should_Return_JsonResponse() throws NLUException {
        //Given
        String text = "Random text to analyse";
        AnalysisResults mockAnalysisResults = mock(AnalysisResults.class);
        String expectResult = "Watson analysis result";

        //When
        when(mockWatsonAnalyzeImpl.analyze(
                any(WatsonAnalyzeOptions.class))
        ).thenReturn(mockAnalysisResults);

        doReturn(mockAnalysisResults).when(mockWatsonAnalyzeImpl).analyze(
                any(WatsonAnalyzeOptions.class));

        when(mockAnalysisResults.toString())
                .thenReturn(expectResult);
        //Then
        String result = watsonNLUServiceImplUnderTest.startAnalysis(text);

        //Verify
        assertThat(result).isEqualTo(expectResult);
        verify(mockWatsonAnalyzeImpl).analyze(any(WatsonAnalyzeOptions.class));
    }
    @Test
    void if_When_Analyze_Any_Exception_Is_Throw_Should_Throw_NLUException() throws NLUException {
        //Given
        String text = "Random text to analyse";

        //When
        when(mockWatsonAnalyzeImpl.analyze(
                any(WatsonAnalyzeOptions.class))
        ).thenThrow(new RuntimeException());

        //Then
        assertThatThrownBy(() -> watsonNLUServiceImplUnderTest.startAnalysis(text))
                .isInstanceOf(NLUException.class)
                .hasMessage("Failed to analyze text");
        //Verify
        verify(mockWatsonAnalyzeImpl).analyze(any(WatsonAnalyzeOptions.class));
        verifyNoMoreInteractions(mockWatsonAnalyzeImpl);

    }
}