package com.example.newsbackend.service.impl;

import com.example.newsbackend.entity.sites.RegisteredSite;
import com.example.newsbackend.exception.PageValidatorException;
import com.example.newsbackend.service.ScrapeNewsPageService;
import com.example.newsbackend.exception.ScrapingException;
import com.example.newsbackend.service.impl.scrape.PageBody;
import com.example.newsbackend.service.impl.scrape.stable.ParseValues;
import com.example.newsbackend.service.impl.serp.NewsResultPage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScrapeNewsPageServiceImpl implements ScrapeNewsPageService {
    private final PageScrapeServiceImpl pageScrapeServiceImpl;

    public ScrapeNewsPageServiceImpl(PageScrapeServiceImpl pageScrapeServiceImpl) {
        this.pageScrapeServiceImpl = pageScrapeServiceImpl;
    }


    @Override
    public PageBody scrapeNewsPages(NewsResultPage searchResult, RegisteredSite registeredSite) throws PageValidatorException, ScrapingException {
        String searchUrl = searchResult.getLink();

        List<ParseValues> parseValues = getParseValueByUrl(registeredSite,searchUrl);
        String txtValues = parseValues
                .stream()
                .map(parseValue -> parseValue.getValues().get("text"))
                .reduce("", (a, b) -> a + " " + b);
        PageBody pageBody = new PageBody(searchResult,txtValues,registeredSite);
        return pageBody;
    }

    private List<ParseValues> getParseValueByUrl(RegisteredSite registeredSite, String url) throws PageValidatorException, ScrapingException {
        List<ParseValues>  parseValues = scrapeRegisteredSite(registeredSite, url);
        return parseValues;
    }

    private  List<ParseValues>  scrapeRegisteredSite(RegisteredSite registeredSite, String url) throws ScrapingException {
        List<ParseValues> pagesContents = pageScrapeServiceImpl.scrape(registeredSite.getSiteConfiguration(), url);
        return pagesContents;
    }


}
