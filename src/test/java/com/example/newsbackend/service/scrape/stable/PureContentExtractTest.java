package com.example.newsbackend.service.scrape.stable;

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
class PureContentExtractTest {

    @Mock
    private DownloadPage mockDownloadPage;
    @Mock
    private HtmlExtractor mockHtmlExtractor;

    private PureContentExtract pureContentExtractUnderTest;

    @BeforeEach
    void setUp() {
        pureContentExtractUnderTest = new PureContentExtract(mockDownloadPage, mockHtmlExtractor);
    }

    @Test
    void when_ExtractDownloadPage_Should_Return_PageContent() throws Exception {
        // Given
        final InputStream spyInputStream = spy(new ByteArrayInputStream("result".getBytes()));

        //When
        when(mockDownloadPage.getPageInputStream(new URL("https://example.com/"))).thenReturn(spyInputStream);
        when(mockHtmlExtractor.extractHtmlFromInputStream(spyInputStream)).thenReturn("result");

        // Then
        final String result = pureContentExtractUnderTest.extract("https://example.com/");

        // Verify
        assertThat(result).isEqualTo("result");
        verify(mockDownloadPage,times(1)).getPageInputStream(any(URL.class));
        verify(mockHtmlExtractor,times(1)).extractHtmlFromInputStream(any(InputStream.class));
    }

    @Test
    void when_ExtractDownloadPage_Should_Returns_NoContent() throws Exception {
        // Given
        final InputStream spyInputStream = spy(InputStream.nullInputStream());

        // When
        when(mockDownloadPage.getPageInputStream(new URL("https://example.com/"))).thenReturn(spyInputStream);
        when(mockHtmlExtractor.extractHtmlFromInputStream(spyInputStream)).thenReturn("");

        // Then
        final String result = pureContentExtractUnderTest.extract("https://example.com/");

        // Verify
        assertThat(result).isEmpty();
    }



    @Test
    void if_When_Extract_DownloadPage_ThrowsIOException() throws Exception {
        // When
        when(mockDownloadPage.getPageInputStream(new URL("https://example.com/"))).thenThrow(IOException.class);

        // Verify
        assertThatThrownBy(() -> pureContentExtractUnderTest.extract("https://example.com/")).isInstanceOf(IOException.class);
    }

    @Test
    void if_When_ExtractHtmlExtractor_ThrowsIOException() throws Exception {
        // Given
        final InputStream spyInputStream = spy(new ByteArrayInputStream("content".getBytes()));
        //When
        when(mockDownloadPage.getPageInputStream(new URL("https://example.com/"))).thenReturn(spyInputStream);
        when(mockHtmlExtractor.extractHtmlFromInputStream(spyInputStream)).thenThrow(IOException.class);

        // Verify
        assertThatThrownBy(() -> pureContentExtractUnderTest.extract("https://example.com/")).isInstanceOf(IOException.class);

    }
}
