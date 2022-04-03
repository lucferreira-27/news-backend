package com.example.newsbackend.service.scrape.stable;

import com.example.newsbackend.repository.sites.SelectorQuery;
import com.example.newsbackend.service.scrape.SelectorQueryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class HtmlParserTest {

    private HtmlParser htmlParserUnderTest;

    @BeforeEach
    void setUp() {
        htmlParserUnderTest = new HtmlParser();
    }

    @Test
    void when_Parse_Html_Should_Return_ParseValues() throws IOException {
        // Given
        final SelectorQuery titleSelectorQuery = new SelectorQuery();
        titleSelectorQuery.setSelector("div.c_title");
        titleSelectorQuery.setAttribute("text");
        final SelectorQuery urlSelectorQuery = new SelectorQuery();
        urlSelectorQuery.setSelector("a.c_href");
        urlSelectorQuery.setAttribute("href");

        final List<SelectorQuery> scrapeQueries = List.of(titleSelectorQuery, urlSelectorQuery);
        final String testHtml = readParseTestHtmlFile("parseTest_Default.html");
        // Then
        final List<ParseValues> result = htmlParserUnderTest.parse(testHtml, scrapeQueries);

        // Verify
        assertThat(result).hasSize(3);
        assertThat(result.get(0).getConcatenatedJsonValues()).isEqualTo("{\"text\":\"Title 01\", \"href\":\"http://www.01.com\"}");
        assertThat(result.get(0).getValues()).containsExactly("{\"text\":\"Title 01\"}", "{\"href\":\"http://www.01.com\"}");
        assertThat(result.get(1).getValues()).containsExactly("{\"text\":\"Title 02\"}", "{\"href\":\"http://www.02.com\"}");
        assertThat(result.get(2).getValues()).containsExactly("{\"text\":\"Title 03\"}", "{\"href\":\"http://www.03.com\"}");
        assertThat(result.get(0).getJsonValues().path("text").asText()).isEqualTo("Title 01");
        assertThat(result.get(0).getJsonValues().path("href").asText()).isEqualTo("http://www.01.com");
        assertThat(result.get(1).getJsonValues().path("text").asText()).isEqualTo("Title 02");
        assertThat(result.get(1).getJsonValues().path("href").asText()).isEqualTo("http://www.02.com");
        assertThat(result.get(2).getJsonValues().path("text").asText()).isEqualTo("Title 03");
        assertThat(result.get(2).getJsonValues().path("href").asText()).isEqualTo("http://www.03.com");

    }

    @Test
    void if_Html_Null_Or_Empty_Should_Throw_IllegalArgumentException(){
        // Given
        final SelectorQuery titleSelectorQuery = new SelectorQuery();
        titleSelectorQuery.setSelector("div.c_title");
        titleSelectorQuery.setAttribute("text");
        final SelectorQuery urlSelectorQuery = new SelectorQuery();
        urlSelectorQuery.setSelector("a.c_href");
        urlSelectorQuery.setAttribute("href");

        final List<SelectorQuery> scrapeQueries = List.of(titleSelectorQuery, urlSelectorQuery);
        final String testHtml = "";
        // Then
        // Verify
        assertThatThrownBy(() -> htmlParserUnderTest.parse(testHtml, scrapeQueries))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Html is null or empty");
    }

    @Test
    void when_Html_InnerText_Empty_Should_Return_ParseValues_With_No_Text() throws IOException {
        // Given
        final SelectorQuery titleSelectorQuery = new SelectorQuery();
        titleSelectorQuery.setSelector("div.c_title");
        titleSelectorQuery.setAttribute("text");
        final SelectorQuery urlSelectorQuery = new SelectorQuery();
        urlSelectorQuery.setSelector("a.c_href");
        urlSelectorQuery.setAttribute("href");

        final List<SelectorQuery> scrapeQueries = List.of(titleSelectorQuery, urlSelectorQuery);

        final String testHtml = readParseTestHtmlFile("parseTest_EmptyInnerText.html");
        // Then
        final List<ParseValues> result = htmlParserUnderTest.parse(testHtml, scrapeQueries);

        // Verify
        assertThat(result).hasSize(3);
        assertThat(result.get(0).getJsonValues().path("text").asText()).isEmpty();
        assertThat(result.get(0).getJsonValues().path("href").asText()).isEqualTo("http://www.01.com");
        assertThat(result.get(1).getJsonValues().path("text").asText()).isEmpty();
        assertThat(result.get(1).getJsonValues().path("href").asText()).isEqualTo("http://www.02.com");
        assertThat(result.get(2).getJsonValues().path("text").asText()).isEmpty();
        assertThat(result.get(2).getJsonValues().path("href").asText()).isEqualTo("http://www.03.com");

    }
    @Test
    void if_SelectorQuery_Invalid_Should_Throw_SelectorQueryException() throws IOException {
        // Given
        final SelectorQuery invalidSelectorQuery = new SelectorQuery();
        invalidSelectorQuery.setSelector(".___invalid___");
        final String exceptionMessage = "No elements found for selector: " + invalidSelectorQuery.getSelector();

        final List<SelectorQuery> scrapeQueries = List.of(invalidSelectorQuery);

        final String testHtml = readParseTestHtmlFile("parseTest_Default.html");

        // Then
        assertThatThrownBy(() -> htmlParserUnderTest.parse(testHtml, scrapeQueries))
                .isInstanceOf(SelectorQueryException.class)
                .hasMessage(exceptionMessage);




    }

    private String readParseTestHtmlFile(String name) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(name).getFile());
        String result = Files.readString(Paths.get(file.getAbsolutePath()));
        return result;
    }

}
