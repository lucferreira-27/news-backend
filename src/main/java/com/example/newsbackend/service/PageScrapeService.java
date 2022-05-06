package com.example.newsbackend.service;

import com.example.newsbackend.entity.sites.SiteConfiguration;
import com.example.newsbackend.exception.ScrapingException;
import com.example.newsbackend.service.impl.scrape.stable.ParseValues;

import java.util.List;

public interface PageScrapeService {
    public List<ParseValues> scrape(SiteConfiguration siteConfiguration, String url) throws ScrapingException;
}
