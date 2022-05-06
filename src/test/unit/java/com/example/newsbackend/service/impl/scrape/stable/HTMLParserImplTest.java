package com.example.newsbackend.service.impl.scrape.stable;

import com.example.newsbackend.entity.sites.SelectorQuery;
import com.example.newsbackend.exception.SelectorQueryException;
import org.assertj.core.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class HTMLParserImplTest {

    private HTMLParserImpl htmlParserUnderTest;

    @BeforeEach
    void setUp() {
        htmlParserUnderTest = new HTMLParserImpl();
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
        Assertions.assertThat(result).hasSize(3);
        Assertions.assertThat(result.get(0).getValues().get("text")).isEqualTo("Title 01");
        Assertions.assertThat(result.get(0).getValues().get("href")).isEqualTo("http://www.01.com");
        Assertions.assertThat(result.get(1).getValues().get("text")).isEqualTo("Title 02");
        Assertions.assertThat(result.get(1).getValues().get("href")).isEqualTo("http://www.02.com");
        Assertions.assertThat(result.get(2).getValues().get("text")).isEqualTo("Title 03");
        Assertions.assertThat(result.get(2).getValues().get("href")).isEqualTo("http://www.03.com");


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
        assertThat(result,hasSize(3));

        MatcherAssert.assertThat(result.get(0).getValues().get("text"), is(emptyString()));
        MatcherAssert.assertThat(result.get(0).getValues().get("href"), is("http://www.01.com"));
        MatcherAssert.assertThat(result.get(1).getValues().get("text"), is(emptyString()));
        MatcherAssert.assertThat(result.get(1).getValues().get("href"), is("http://www.02.com"));
        MatcherAssert.assertThat(result.get(2).getValues().get("text"), is(emptyString()));
        MatcherAssert.assertThat(result.get(2).getValues().get("href"), is("http://www.03.com"));


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
