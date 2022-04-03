package com.example.newsbackend.service;

import com.example.newsbackend.repository.page.PageContent;
import com.example.newsbackend.repository.page.PageHeadline;
import com.example.newsbackend.repository.search.SearchEngine;
import com.example.newsbackend.repository.search.SearchEngineRepository;
import com.example.newsbackend.repository.sites.SiteConfiguration;
import com.example.newsbackend.service.scrape.dynamic.DynamicScrapingBot;
import com.example.newsbackend.service.scrape.ScrapingException;
import com.example.newsbackend.service.scrape.stable.ParseValues;
import com.example.newsbackend.service.tools.PageScrapeTool;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SearchNewsPageServiceImpl implements SearchNewsPageService {


    private final SearchQueryBuilder searchQueryBuilder;
    private final PageScrapeTool pageScrapeTool;
    private final SearchEngineRepository searchEngineRepository;
    @Autowired
    public SearchNewsPageServiceImpl(SearchQueryBuilder searchQueryBuilder, PageScrapeTool pageScrapeTool, SearchEngineRepository searchEngineRepository) {
        this.searchQueryBuilder = searchQueryBuilder;
        this.pageScrapeTool = pageScrapeTool;
        this.searchEngineRepository = searchEngineRepository;
    }

    public List<PageHeadline> search(String keyword) throws ScrapingException {


        SearchEngine searchEngine = getActiveSearchEngine()
                .orElseThrow(() -> new ScrapingException("No active search engine"));
        String urlQuery = searchQueryBuilder.build(keyword, searchEngine);
        SiteConfiguration siteConfiguration = searchEngine.getSiteConfiguration();
        List<ParseValues> parseValues = pageScrapeTool.scrape(siteConfiguration,urlQuery);
        return pageContentToHeadline(parseValues);
    }

    private List<PageHeadline> pageContentToHeadline(List<ParseValues> parseValues) {

        List<PageHeadline> pageHeadlines = parseValues
                .stream()
                .map(AttributesContent::contentToHeadline)
                .collect(Collectors.toList());
        return pageHeadlines;
    }


    private Optional<SearchEngine> getActiveSearchEngine() {
        Iterator<SearchEngine> iterator = searchEngineRepository.findByActiveTrue().iterator();
        if (!iterator.hasNext()) {
            return Optional.empty();
        }

        return Optional.of(iterator.next());
    }
}
