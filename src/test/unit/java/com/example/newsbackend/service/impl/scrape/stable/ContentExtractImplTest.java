package com.example.newsbackend.service.impl.scrape.stable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContentExtractImplTest {

    @Mock
    private DownloadPageImpl mockDownloadPageImpl;
    @Mock
    private TextExtractorImpl mockTextExtractorImpl;

    private ContentExtractImpl contentExtractImplUnderTest;

    @BeforeEach
    void setUp() {
        contentExtractImplUnderTest = new ContentExtractImpl(mockDownloadPageImpl, mockTextExtractorImpl);
    }

    @Test
    void when_ExtractDownloadPage_Should_Return_PageContent() throws Exception {
        // Given
        final InputStream spyInputStream = spy(new ByteArrayInputStream("result".getBytes()));

        //When
        when(mockDownloadPageImpl.getPageInputStream(new URL("https://example.com/"))).thenReturn(spyInputStream);
        when(mockTextExtractorImpl.extractTextFromInputStream(spyInputStream)).thenReturn("result");

        // Then
        final String result = contentExtractImplUnderTest.extract("https://example.com/");

        // Verify
        assertThat(result).isEqualTo("result");
        verify(mockDownloadPageImpl,times(1)).getPageInputStream(any(URL.class));
        verify(mockTextExtractorImpl,times(1)).extractTextFromInputStream(any(InputStream.class));
    }

    @Test
    void when_ExtractDownloadPage_Should_Returns_NoContent() throws Exception {
        // Given
        final InputStream spyInputStream = spy(InputStream.nullInputStream());

        // When
        when(mockDownloadPageImpl.getPageInputStream(new URL("https://example.com/"))).thenReturn(spyInputStream);
        when(mockTextExtractorImpl.extractTextFromInputStream(spyInputStream)).thenReturn("");

        // Then
        final String result = contentExtractImplUnderTest.extract("https://example.com/");

        // Verify
        assertThat(result).isEmpty();
    }



    @Test
    void if_When_Extract_DownloadPage_ThrowsIOException() throws Exception {
        // When
        when(mockDownloadPageImpl.getPageInputStream(new URL("https://example.com/"))).thenThrow(IOException.class);

        // Verify
        assertThatThrownBy(() -> contentExtractImplUnderTest.extract("https://example.com/")).isInstanceOf(IOException.class);
    }

    @Test
    void if_When_ExtractHtmlExtractor_ThrowsIOException() throws Exception {
        // Given
        final InputStream spyInputStream = spy(new ByteArrayInputStream("content".getBytes()));
        //When
        when(mockDownloadPageImpl.getPageInputStream(new URL("https://example.com/"))).thenReturn(spyInputStream);
        when(mockTextExtractorImpl.extractTextFromInputStream(spyInputStream)).thenThrow(IOException.class);

        // Verify
        assertThatThrownBy(() -> contentExtractImplUnderTest.extract("https://example.com/")).isInstanceOf(IOException.class);

    }
}
