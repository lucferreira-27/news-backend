package com.example.newsbackend.service.tools;

import com.example.newsbackend.repository.page.PageContent;
import com.example.newsbackend.repository.sites.SiteConfiguration;
import com.example.newsbackend.service.scrape.ScrapingException;
import com.example.newsbackend.service.scrape.ScrapingStrategy;
import com.example.newsbackend.service.scrape.stable.ParseValues;

import java.util.List;

public interface PageScrapeService {
    public List<ParseValues> scrape(SiteConfiguration siteConfiguration, String url) throws ScrapingException;
}
