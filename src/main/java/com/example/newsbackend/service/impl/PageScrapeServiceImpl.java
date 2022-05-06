package com.example.newsbackend.service.impl;

import com.example.newsbackend.entity.sites.SiteConfiguration;
import com.example.newsbackend.exception.ScrapingException;
import com.example.newsbackend.service.impl.scrape.ScrapingStrategyAbstract;
import com.example.newsbackend.service.impl.scrape.stable.ParseValues;
import com.example.newsbackend.service.PageScrapeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PageScrapeServiceImpl implements PageScrapeService {

    private final Map<String, ScrapingStrategyAbstract> scrapingStrategies;

    public PageScrapeServiceImpl(Map<String, ScrapingStrategyAbstract> scrapingStrategies) {
        this.scrapingStrategies = scrapingStrategies;
    }

    @Override
    public List<ParseValues> scrape(SiteConfiguration siteConfiguration, String url) throws ScrapingException {

        ScrapingStrategyAbstract scrapingStrategyAbstract = scrapingStrategies.get(siteConfiguration.getScrapingType().name());
        if (scrapingStrategyAbstract == null) {
            throw new NullPointerException("No scraping strategy found for " + siteConfiguration.getScrapingType().name());
        }
        if(siteConfiguration.getSelectorQueries() == null || siteConfiguration.getSelectorQueries().isEmpty()) {
            throw new NullPointerException("No scrape queries found for " + siteConfiguration.getDomain());
        }

        return scrapingStrategyAbstract.extractPageContents(url, siteConfiguration);
    }


}
