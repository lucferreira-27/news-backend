package com.example.newsbackend.service.impl;

import com.example.newsbackend.entity.sites.RegisteredSite;
import com.example.newsbackend.entity.search.SearchInfoResult;
import com.example.newsbackend.entity.search.StorageResult;
import com.example.newsbackend.entity.search.StorageStatus;
import com.example.newsbackend.repository.storage.*;
import com.example.newsbackend.entity.nlu.WatsonAnaliseResult;
import com.example.newsbackend.exception.PageValidatorException;
import com.example.newsbackend.service.ResultAnalysisService;
import com.example.newsbackend.service.impl.nlu.watson.WatsonNLUServiceImpl;
import com.example.newsbackend.exception.ScrapingException;
import com.example.newsbackend.service.impl.scrape.PageBody;
import com.example.newsbackend.service.impl.serp.NewsResultPage;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ResultAnalysisServiceImpl implements ResultAnalysisService {

    private final ParseNewsServiceImpl parseNewsService;
    private final WatsonNLUServiceImpl watsonNLUServiceImpl;
    private final StorageResultRepository storageResultRepository;
    private final Map<Class<? extends Exception>, String> mapStatusMessages = new HashMap<>();
    private final Map<Class<? extends Exception>, StorageStatus> mapStatus = new HashMap<>();

    {
        mapStatusMessages.put(PageValidatorException.class, "Page site isn't registered in database");
        mapStatusMessages.put(ScrapingException.class, "Scraping failed because query proved was invalid");
        mapStatus.put(PageValidatorException.class, StorageStatus.NOT_FOUND);
        mapStatus.put(ScrapingException.class, StorageStatus.INVALID_QUERY);

    }

    public ResultAnalysisServiceImpl(ParseNewsServiceImpl parseNewsService, WatsonNLUServiceImpl watsonNLUServiceImpl,
                                     StorageResultRepository storageResultRepository) {
        this.parseNewsService = parseNewsService;
        this.watsonNLUServiceImpl = watsonNLUServiceImpl;
        this.storageResultRepository = storageResultRepository;
    }


    @Override
    public StorageResult analysis(NewsResultPage newsResultPage) {
        Optional<StorageResult> storageResult = storageResultRepository.findBySearchInfoUrl(newsResultPage.getLink());
        return  storageResult.orElseGet(() -> createStorageResult(new StorageResult(), newsResultPage));
    }

    private StorageResult createStorageResult(StorageResult storageResult, NewsResultPage newsResultPage) {
        long time = initTime();
        try {
            RegisteredSite registeredSite = validatePage(storageResult, newsResultPage.getLink());
            PageBody pageBody = parseNewsService.getNewsPageBody(newsResultPage, registeredSite);
            String jsonAnalyseResult = watsonNLUServiceImpl.startAnalysis(pageBody.getTextContent());
            setAnalysisJsonResponseInStorage(storageResult, jsonAnalyseResult);
        } catch (Exception e) {
            e.printStackTrace();
            storageResult.setStatus(mapStatus.get(e.getClass()), mapStatusMessages.get(e.getClass()));
            if (storageResult.getStatus() == null) {
                storageResult.setStatus(StorageStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
            }

        } finally {
            storageResult.setAnalysisTime(endTime(time));
            setSearchInfoFromNewsPage(newsResultPage, storageResult);
            return storageResult;
        }

    }

    private long initTime() {
        return System.currentTimeMillis();
    }

    private long endTime(long startTime) {
        long time = System.currentTimeMillis() - startTime;
        return time;
    }

    private RegisteredSite validatePage(StorageResult storageResult, String pageUrl) throws PageValidatorException {
        RegisteredSite registeredSite = parseNewsService.validateNewsSite(pageUrl);
        storageResult.setRegisteredSite(registeredSite);
        return registeredSite;
    }

    private void setAnalysisJsonResponseInStorage(StorageResult storageResult, String jsonResponse) throws JsonProcessingException {
        WatsonAnaliseResult watsonAnaliseResult = WatsonAnaliseResult.fromJson(jsonResponse);
        watsonAnaliseResult.getConcepts().forEach(concept -> concept.setWatsonAnaliseResult(watsonAnaliseResult));
        watsonAnaliseResult.getEntities().forEach(entity -> entity.setWatsonAnaliseResult(watsonAnaliseResult));
        watsonAnaliseResult.getKeywords().forEach(keyword -> keyword.setWatsonAnaliseResult(watsonAnaliseResult));

        storageResult.setContentAnaliseResult(watsonAnaliseResult);
        storageResult.setStatus(StorageStatus.SUCCESS, "OK");
    }

    private void setSearchInfoFromNewsPage(NewsResultPage newsResultPage, StorageResult storageResult) {
        SearchInfoResult searchInfoResult = new SearchInfoResult();
        searchInfoResult.setHeadline(newsResultPage.getTitle());
        searchInfoResult.setUrl(newsResultPage.getLink());
        searchInfoResult.setSnippet(newsResultPage.getSnippet());
        storageResult.setSearchInfo(searchInfoResult);
    }
}
