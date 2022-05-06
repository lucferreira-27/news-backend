package com.example.newsbackend.service.impl.scrape;

import com.example.newsbackend.exception.ScrapingException;
import com.example.newsbackend.entity.sites.SiteConfiguration;
import com.example.newsbackend.service.impl.scrape.stable.HTMLParserImpl;
import com.example.newsbackend.service.impl.scrape.stable.ParseValues;

import java.util.List;


public abstract class ScrapingStrategyAbstract {

    private final HTMLParserImpl htmlParser;

    protected ScrapingStrategyAbstract(HTMLParserImpl htmlParser) {
        this.htmlParser = htmlParser;
    }

    public List<ParseValues> extractPageContents(String url, SiteConfiguration siteConfiguration) throws ScrapingException {
        try {
            String contentExtract = scrapePage(url);
            List<ParseValues> pageContents = parseContentExtract(contentExtract, siteConfiguration);
            return pageContents;
        } catch (Exception e) {
            throw new ScrapingException("Error while getting page contents",e);
        }
    }

    protected abstract String scrapePage(String url) throws Exception;

    protected List<ParseValues> parseContentExtract(String contentExtract, SiteConfiguration siteConfiguration) {

        List<ParseValues> pageHeadlines = htmlParser.parse(contentExtract, siteConfiguration.getSelectorQueries());
        return pageHeadlines;
    }
}
