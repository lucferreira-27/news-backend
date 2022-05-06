package com.example.newsbackend.service;

import com.example.newsbackend.exception.PageValidatorException;
import com.example.newsbackend.service.impl.scrape.PageBody;
import com.example.newsbackend.entity.sites.RegisteredSite;
import com.example.newsbackend.exception.ScrapingException;
import com.example.newsbackend.service.impl.serp.NewsResultPage;

public interface ScrapeNewsPageService {
    public PageBody scrapeNewsPages(NewsResultPage headlinesNews,RegisteredSite registeredSite) throws PageValidatorException, ScrapingException;

}
