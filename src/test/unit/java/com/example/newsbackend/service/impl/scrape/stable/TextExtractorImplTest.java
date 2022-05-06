package com.example.newsbackend.service.impl.scrape.stable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TextExtractorImplTest {

    private TextExtractorImpl textExtractorImplUnderTest;

    @BeforeEach
    void setUp() {
        textExtractorImplUnderTest = new TextExtractorImpl();
    }

    @Test
    void when_ExtractHtmlFromInputStream_Should_Return_InputContent_In_String() throws IOException {
        // Given
        final String expectResult = "testContent";
        final InputStream inputStream = new ByteArrayInputStream(expectResult.getBytes());

        // Then
        final String result = textExtractorImplUnderTest.extractTextFromInputStream(inputStream);

        // Verify
        assertThat(result).isEqualTo(expectResult);
        assertThat(inputStream.available()).isEqualTo(0);
    }

    @Test
    void when_ExtractHtmlFromEmptyInputStream_Should_Return_Empty_String() throws IOException {
        // Setup
        final InputStream inputStream = InputStream.nullInputStream();
        final String expectResult = "";
        // Run the test
        final String result = textExtractorImplUnderTest.extractTextFromInputStream(inputStream);

        // Verify the results
        assertThat(result).isEqualTo(expectResult);
    }

    @Test
    void if_InputStream_Read_is_Broken_Should_Throw_IOException() throws IOException {
        // Setup
        final InputStream mockInputStream = mock(InputStream.class);

        //When
        when(mockInputStream.read()).thenThrow(IOException.class);

        // Verify
        assertThatThrownBy(() -> textExtractorImplUnderTest.extractTextFromInputStream(mockInputStream))
                .isInstanceOf(IOException.class);
    }


}
