package com.example.newsbackend.service.scrape.stable;

import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

class DownloadPageTest {

    private DownloadPage downloadPageUnderTest;



    @BeforeEach
    void setUp() {
        downloadPageUnderTest = new DownloadPage();
    }
    @Test
    void when_GetPageInputStream_Given_URL_Should_Return_InputStream() throws Exception {
        //Given
        URL mockUrl = mock(URL.class);
        URLConnection mockUrlConnection = mock(URLConnection.class);
        InputStream expectResult = new ByteArrayInputStream("content".getBytes());

        //when
        when(mockUrl.openConnection()).thenReturn(mockUrlConnection);
        when(mockUrlConnection.getInputStream()).thenReturn(expectResult);
        // Then
        final InputStream result = downloadPageUnderTest.getPageInputStream(mockUrl);

        // Verify
        assertThat(result).isEqualTo(expectResult);
    }

    @Test
    void if_Failed_On_OpenConnection_Should_Thrown_IOException() throws IOException {
        //Given
        URL mockUrl = mock(URL.class);
        //When
        doThrow(new IOException()).when(mockUrl).openConnection();
        //Then
        assertThatThrownBy(() -> downloadPageUnderTest.getPageInputStream(mockUrl))
                .isInstanceOf(IOException.class);
    }


}
