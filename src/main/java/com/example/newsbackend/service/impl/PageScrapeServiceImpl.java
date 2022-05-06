package com.example.newsbackend.service.impl;

import com.example.newsbackend.repository.sites.SiteConfiguration;
import com.example.newsbackend.service.scrape.ScrapingException;
import com.example.newsbackend.service.scrape.ScrapingStrategy;
import com.example.newsbackend.service.scrape.stable.ParseValues;
import com.example.newsbackend.service.PageScrapeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PagePageScrapeServiceImpl implements PageScrapeService {

    private final Map<String, ScrapingStrategy> scrapingStrategies;

    public PagePageScrapeServiceImpl(Map<String, ScrapingStrategy> scrapingStrategies) {
        this.scrapingStrategies = scrapingStrategies;
    }

    @Override
    public List<ParseValues> scrape(SiteConfiguration siteConfiguration, String url) throws ScrapingException {

        ScrapingStrategy scrapingStrategy = scrapingStrategies.get(siteConfiguration.getScrapingType().name());
        if (scrapingStrategy == null) {
            throw new NullPointerException("No scraping strategy found for " + siteConfiguration.getScrapingType().name());
        }
        if(siteConfiguration.getSelectorQueries() == null || siteConfiguration.getSelectorQueries().isEmpty()) {
            throw new NullPointerException("No scrape queries found for " + siteConfiguration.getDomain());
        }

        return scrapingStrategy.extractPageContents(url, siteConfiguration);
    }


}
