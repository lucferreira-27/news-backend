package com.example.newsbackend.service;

import com.example.newsbackend.repository.page.PageBody;
import com.example.newsbackend.repository.page.PageHeadline;
import com.example.newsbackend.repository.sites.RegisteredSite;
import com.example.newsbackend.service.scrape.ScrapingException;
import com.example.newsbackend.service.scrape.stable.ParseValues;
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
    public List<PageBody> scrapeNewsPages(List<PageHeadline> pageHeadlines) throws PageValidatorException, ScrapingException {
        List<PageBody> pagesContents = new ArrayList<>();
        for (PageHeadline headline : pageHeadlines) {
            ParseValues parseValue = getParseValueByUrl(headline.getUrl());
            PageBody pageBody = createPageBody(headline, parseValue.getValues().get(0));
            pagesContents.add(pageBody);
        }
        return pagesContents;
    }

    private ParseValues getParseValueByUrl(String url) throws PageValidatorException, ScrapingException {
        RegisteredSite registeredSite = getRegisteredSiteByURL(url);
        ParseValues parseValue = scrapeRegisteredSite(registeredSite, url);
        return parseValue;
    }

    private RegisteredSite getRegisteredSiteByURL(String url) throws PageValidatorException {
        RegisteredSite registeredSite = pageValidator.validatePage(url);
        return registeredSite;
    }

    private ParseValues scrapeRegisteredSite(RegisteredSite registeredSite, String url) throws ScrapingException {
        List<ParseValues> pagesContents = pageScrapeTool.scrape(registeredSite.getSiteConfiguration(), url);
        return pagesContents.get(0);
    }

    private PageBody createPageBody(PageHeadline headline, String textContent) {
        return new PageBody(headline, textContent);
    }


}
