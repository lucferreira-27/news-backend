package com.example.newsbackend.service.impl;

import com.example.newsbackend.entity.sites.RegisteredSite;
import com.example.newsbackend.exception.PageValidatorException;
import com.example.newsbackend.service.ParseNewsService;
import com.example.newsbackend.exception.ScrapingException;
import com.example.newsbackend.service.impl.scrape.PageBody;
import com.example.newsbackend.service.impl.serp.NewsResultPage;
import org.springframework.stereotype.Service;

@Service
public class ParseNewsServiceImpl implements ParseNewsService {

    private final ScrapeNewsPageServiceImpl scrapePageService;
    private final PageValidatorServiceImpl pageValidatorServiceImpl;

    public ParseNewsServiceImpl(ScrapeNewsPageServiceImpl scrapePageService, PageValidatorServiceImpl pageValidatorServiceImpl) {
        this.scrapePageService = scrapePageService;
        this.pageValidatorServiceImpl = pageValidatorServiceImpl;
    }

    @Override
    public RegisteredSite validateNewsSite(String url) throws PageValidatorException {
        return pageValidatorServiceImpl.validatePage(url);
    }

    @Override
    public PageBody getNewsPageBody(NewsResultPage searchResult, RegisteredSite registeredSite) throws
            ScrapingException,
            PageValidatorException {
        return scrapePageService.scrapeNewsPages(searchResult, registeredSite);
    }
}
