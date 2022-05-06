package com.example.newsbackend.service.impl.serp;

import com.example.newsbackend.service.impl.scrape.stable.TextExtractorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HTTPRequestTest {

    @Mock
    private TextExtractorImpl mockTextExtractorImpl;

    private HTTPRequestServiceImpl httpRequestServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        httpRequestServiceImplUnderTest = new HTTPRequestServiceImpl(mockTextExtractorImpl);
    }

    @Test
    void when_SendRequest_Should_Return_StringContent() throws Exception {

        //Given
        final String expected = "content";

        // When
        when(mockTextExtractorImpl.extractTextFromInputStream(any(InputStream.class))).thenReturn(expected);

        // Then
        final String result = httpRequestServiceImplUnderTest.sendRequest(new URL("https://example.com/"));

        // Verify the results
        assertThat(result).isEqualTo(expected);
    }


}
