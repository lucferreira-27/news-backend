package com.example.newsbackend.service;

import com.example.newsbackend.repository.page.PageBody;
import com.example.newsbackend.repository.page.PageHeadline;
import com.example.newsbackend.repository.sites.RegisteredSite;
import com.example.newsbackend.service.scrape.ScrapingException;
import com.example.newsbackend.service.scrape.stable.ParseValues;
import com.example.newsbackend.service.serp.NewsResultPage;
import com.example.newsbackend.service.tools.PageScrapeTool;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScrapeNewsPageServiceImpl implements ScrapeNewsPageService {
    private final PageValidator pageValidator;
    private final PageScrapeTool pageScrapeTool;

    public ScrapeNewsPageServiceImpl(PageValidator pageValidator, PageScrapeTool pageScrapeTool) {
        this.pageValidator = pageValidator;
        this.pageScrapeTool = pageScrapeTool;
    }


    @Override
    public PageBody scrapeNewsPages(NewsResultPage searchResult) throws PageValidatorException, ScrapingException {

        ParseValues parseValue = getParseValueByUrl(searchResult.getLink());
        PageBody pageBody = new PageBody(searchResult, parseValue.getValues().get("text"));
        return pageBody;
    }

    private ParseValues getParseValueByUrl(String url) throws PageValidatorException, ScrapingException {
        RegisteredSite registeredSite = getRegisteredSiteByURL(url);
        ParseValues parseValue = scrapeRegisteredSite(registeredSite, url);
        return parseValue;
    }

    private RegisteredSite getRegisteredSiteByURL(String url) throws PageValidatorException {
        String testUrl = url.substring(0, url.indexOf("/", 8));
        RegisteredSite registeredSite = pageValidator.validatePage(testUrl);
        return registeredSite;
    }

    private ParseValues scrapeRegisteredSite(RegisteredSite registeredSite, String url) throws ScrapingException {
        List<ParseValues> pagesContents = pageScrapeTool.scrape(registeredSite.getSiteConfiguration(), url);
        return pagesContents.get(0);
    }


}
