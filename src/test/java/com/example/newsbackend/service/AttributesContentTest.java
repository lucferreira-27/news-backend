package com.example.newsbackend.service;

import com.example.newsbackend.repository.page.PageHeadline;
import com.example.newsbackend.service.scrape.stable.ParseValues;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

class AttributesContentTest {

    @Test
    void when_Convert_ParseValues_To_PageHeadline_Should_Return_PageHeadline() {
        // Setup
        final ParseValues parseValues = new ParseValues();
        parseValues.addValue("text", "testTitle");
        parseValues.addValue("href", "testUrl");

        // Run the test
        final PageHeadline result = AttributesContent.contentToHeadline(parseValues);

        // Verify the results
        assertThat(result.getTitle()).isEqualTo("testTitle");
        assertThat(result.getUrl()).isEqualTo("testUrl");
    }
    @Test
    void if_Any_ParseValues_Attributes_Not_Found_Should_Throw_AttributesContentException() {
        // Given
        final ParseValues parseValues = new ParseValues();
        parseValues.addValue("data", "testTitle");
        parseValues.addValue("event", "testUrl");

        // Verify the results
        assertThatThrownBy(() -> { AttributesContent.contentToHeadline(parseValues);; })
                .isInstanceOf(AttributesContentException.class)
                .hasMessage("JsonNode from parseValues does not have title or/and url");
    }
    @Test
    void if_ParseValues_Get_JsonValues_Fail_Should_Throw_AttributesContentException() throws JsonProcessingException {
        // Given
        final ParseValues parseValues = spy(new ParseValues());


        //When
        doThrow(JsonProcessingException.class).when(parseValues).getJsonValues();

        // Verify the results
        assertThatThrownBy(() -> { AttributesContent.contentToHeadline(parseValues);; })
                .isInstanceOf(AttributesContentException.class)
                .hasMessage("Error while getting json values from parse values");
    }
}
