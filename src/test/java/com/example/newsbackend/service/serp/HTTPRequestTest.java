package com.example.newsbackend.service.serp;

import com.example.newsbackend.service.scrape.stable.TextExtractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HTTPRequestTest {

    @Mock
    private TextExtractor mockTextExtractor;

    private HTTPRequest httpRequestUnderTest;

    @BeforeEach
    void setUp() {
        httpRequestUnderTest = new HTTPRequest(mockTextExtractor);
    }

    @Test
    void when_SendRequest_Should_Return_StringContent() throws Exception {

        //Given
        final String expected = "content";

        // When
        when(mockTextExtractor.extractTextFromInputStream(any(InputStream.class))).thenReturn(expected);

        // Then
        final String result = httpRequestUnderTest.sendRequest(new URL("https://example.com/"));

        // Verify the results
        assertThat(result).isEqualTo(expected);
    }


}
