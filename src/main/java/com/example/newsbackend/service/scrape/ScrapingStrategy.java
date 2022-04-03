package com.example.newsbackend.service.scrape;

import com.example.newsbackend.repository.page.PageContent;
import com.example.newsbackend.repository.sites.SiteConfiguration;
import com.example.newsbackend.service.scrape.stable.HtmlParser;
import com.example.newsbackend.service.scrape.stable.ParseValues;

import java.util.List;


public abstract class ScrapingStrategy {

    private final HtmlParser htmlParser;

    protected ScrapingStrategy(HtmlParser htmlParser) {
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

        List<ParseValues> pageHeadlines = htmlParser.parse(contentExtract, siteConfiguration.getScrapeQueries());
        return pageHeadlines;
    }
}
