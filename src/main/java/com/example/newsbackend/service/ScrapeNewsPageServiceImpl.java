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
    private final PageScrapeTool pageScrapeTool;

    public ScrapeNewsPageServiceImpl(PageScrapeTool pageScrapeTool) {
        this.pageScrapeTool = pageScrapeTool;
    }


    @Override
    public PageBody scrapeNewsPages(NewsResultPage searchResult,RegisteredSite registeredSite) throws PageValidatorException, ScrapingException {
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
        List<ParseValues> pagesContents = pageScrapeTool.scrape(registeredSite.getSiteConfiguration(), url);
        return pagesContents;
    }


}
