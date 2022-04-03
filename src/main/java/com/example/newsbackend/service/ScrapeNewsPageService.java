package com.example.newsbackend.service;

import com.example.newsbackend.repository.page.PageBody;
import com.example.newsbackend.repository.page.PageContent;
import com.example.newsbackend.repository.page.PageHeadline;
import com.example.newsbackend.service.scrape.ScrapingException;

import java.util.List;

public interface ScrapeNewsPageService {
    public List<PageBody> scrapeNewsPages(List<PageHeadline> headlines) throws PageValidatorException, ScrapingException;
}
