package com.example.newsbackend.service.tools;

import com.example.newsbackend.repository.page.PageContent;
import com.example.newsbackend.repository.sites.SiteConfiguration;
import com.example.newsbackend.service.scrape.ScrapingException;
import com.example.newsbackend.service.scrape.ScrapingStrategy;
import com.example.newsbackend.service.scrape.stable.ParseValues;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PageScrapeTool implements ScrapeTool {

    private final Map<String, ScrapingStrategy> scrapingStrategies;

    public PageScrapeTool(Map<String, ScrapingStrategy> scrapingStrategies) {
        this.scrapingStrategies = scrapingStrategies;
    }

    @Override
    public List<ParseValues> scrape(SiteConfiguration siteConfiguration, String url) throws ScrapingException {

        ScrapingStrategy scrapingStrategy = scrapingStrategies.get(siteConfiguration.getScrapingType().name());
        if (scrapingStrategy == null) {
            throw new NullPointerException("No scraping strategy found for " + siteConfiguration.getScrapingType().name());
        }

        return scrapingStrategy.extractPageContents(url, siteConfiguration);
    }


}
