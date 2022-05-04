package com.example.newsbackend.service;

import com.example.newsbackend.repository.page.PageBody;
import com.example.newsbackend.repository.sites.RegisteredSite;
import com.example.newsbackend.service.scrape.ScrapingException;
import com.example.newsbackend.service.serp.NewsResultPage;

public interface ParseNewsService {
    RegisteredSite validateNewsSite(String url) throws PageValidatorException;
    PageBody getNewsPageBody(NewsResultPage searchResult, RegisteredSite registeredSite) throws ScrapingException, PageValidatorException;
}
