package com.example.newsbackend.service.serp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScaleAPIRequestTest {

    @Mock
    private HTTPRequest mockHttpRequest;
    @Mock
    private URLCustom mockUrlCustom;

    private ScaleAPIRequest scaleAPIRequestUnderTest;

    @BeforeEach
    void setUp() {
        scaleAPIRequestUnderTest = new ScaleAPIRequest(mockHttpRequest, mockUrlCustom);
    }



    @Test
    void when_GetResult_Should_Return_APIResponse() throws Exception {
        // Given
        final int expectedPagesAmount = 10;
        final RequestParameters requestParameters = new RequestParameters.Builder()
                .addApiKey("")
                .addQuery("")
                .addSearchType("")
                .build();
        final String buildUrl = "https://example.com/";
        final String json = readTextFromFile("test_scale_serp_result.json");

        //When
        when(mockUrlCustom.buildParametersURL(anyString(),
                any(RequestParameters.class))).thenReturn(buildUrl);
        when(mockHttpRequest.sendRequest(any(URL.class))).thenReturn(json);

        // Then
        final APIResponse result = scaleAPIRequestUnderTest.getResponse(requestParameters);

        // Verify
        assertThat(result.getNewsResultPages().size()).isEqualTo(expectedPagesAmount);
        assertThat(result.getRequestInfo()).isNotNull();
        verify(mockHttpRequest).sendRequest(any(URL.class));
    }

    private String readTextFromFile(String name) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(name).getFile());
        String result = Files.readString(Paths.get(file.getAbsolutePath()));
        return result;
    }
}
