package com.example.newsbackend.service;

import com.example.newsbackend.repository.page.PageBody;
import com.example.newsbackend.repository.page.PageContent;
import com.example.newsbackend.repository.page.PageHeadline;
import com.example.newsbackend.service.scrape.ScrapingException;
import com.example.newsbackend.service.serp.NewsResultPage;

import java.util.List;

public interface ScrapeNewsPageService {
    public PageBody scrapeNewsPages(NewsResultPage headlines) throws PageValidatorException, ScrapingException;

}
