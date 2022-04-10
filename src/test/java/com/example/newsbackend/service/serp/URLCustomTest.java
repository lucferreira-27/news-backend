package com.example.newsbackend.service.serp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class URLCustomTest {

    private URLCustom urlCustomUnderTest;

    @BeforeEach
    void setUp() {
        urlCustomUnderTest = new URLCustom();
    }

    @Test
    void testBuildParametersURL() {
        // Given
        final String expectResult = "baseUrl?api_key=test_apiKey&q=test_query&search_type=test_searchType&location=test_location&time_period=test_timePeriod&sort_by=test_sortBy&device=test_device&gl=test_googleCountry&include_html=true";
        final RequestParameters requestParameters = new RequestParameters.Builder()
                .addApiKey("test_apiKey")
                .addQuery("test_query")
                .addSearchType("test_searchType")
                .addDevice("test_device")
                .addGoogleCountry("test_googleCountry")
                .addIncludeHtml(true)
                .addLocation("test_location")
                .addTimePeriod("test_timePeriod")
                .addSortBy("test_sortBy")
                .build();

        // Then
        final String result = urlCustomUnderTest.buildParametersURL("baseUrl", requestParameters);
        // Verify
        assertThat(result).isEqualTo(expectResult);
    }
}
