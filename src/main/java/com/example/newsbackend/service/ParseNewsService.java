package com.example.newsbackend.service;

import com.example.newsbackend.exception.PageValidatorException;
import com.example.newsbackend.service.impl.scrape.PageBody;
import com.example.newsbackend.entity.sites.RegisteredSite;
import com.example.newsbackend.exception.ScrapingException;
import com.example.newsbackend.service.impl.serp.NewsResultPage;

public interface ParseNewsService {
    RegisteredSite validateNewsSite(String url) throws PageValidatorException;
    PageBody getNewsPageBody(NewsResultPage searchResult, RegisteredSite registeredSite) throws ScrapingException, PageValidatorException;
}
