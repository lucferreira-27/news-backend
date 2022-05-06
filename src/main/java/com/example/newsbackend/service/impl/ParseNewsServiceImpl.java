package com.example.newsbackend.service;

import com.example.newsbackend.repository.page.PageBody;
import com.example.newsbackend.repository.sites.RegisteredSite;
import com.example.newsbackend.service.scrape.ScrapingException;
import com.example.newsbackend.service.serp.NewsResultPage;
import org.springframework.stereotype.Service;

@Service
public class ParseNewsServiceImpl implements ParseNewsService {

    private final ScrapeNewsPageServiceImpl scrapePageService;
    private final PageValidator pageValidator;

    public ParseNewsServiceImpl(ScrapeNewsPageServiceImpl scrapePageService, PageValidator pageValidator) {
        this.scrapePageService = scrapePageService;
        this.pageValidator = pageValidator;
    }

    @Override
    public RegisteredSite validateNewsSite(String url) throws PageValidatorException {
        return pageValidator.validatePage(url);
    }

    @Override
    public PageBody getNewsPageBody(NewsResultPage searchResult, RegisteredSite registeredSite) throws
            ScrapingException,
            PageValidatorException {
        return scrapePageService.scrapeNewsPages(searchResult, registeredSite);
    }
}
