package com.example.newsbackend.service;

import com.example.newsbackend.repository.search.SearchEngine;
import com.example.newsbackend.repository.sites.SelectorQuery;
import com.example.newsbackend.repository.sites.SiteConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SearchQueryBuilderTest {

    private SearchQueryBuilder searchQueryBuilderUnderTest;

    @BeforeEach
    void setUp() {
        searchQueryBuilderUnderTest = new SearchQueryBuilder();
    }

    @Test
    void when_Build_Should_Return_SearchQuery() {
        // Setup
        final String searchTerm = "term";
        final SearchEngine searchEngine = new SearchEngine();
        final String queryUrl = "queryUrl";
        searchEngine.setQueryUrl(queryUrl);
        final String expectResult  = queryUrl + searchTerm;
        // Run the test
        final String result = searchQueryBuilderUnderTest.build(searchTerm, searchEngine);

        // Verify the results
        assertThat(result).isEqualTo(expectResult);
    }
}
