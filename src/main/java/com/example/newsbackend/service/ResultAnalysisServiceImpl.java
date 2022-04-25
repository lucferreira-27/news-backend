package com.example.newsbackend.service;

import com.example.newsbackend.repository.storage.*;
import com.example.newsbackend.repository.storage.analise.WatsonAnaliseResult;
import com.example.newsbackend.repository.page.PageBody;
import com.example.newsbackend.service.nlu.NLUException;
import com.example.newsbackend.service.nlu.watson.WatsonNLUService;
import com.example.newsbackend.service.scrape.ScrapingException;
import com.example.newsbackend.service.serp.NewsResultPage;
import org.springframework.stereotype.Service;

@Service
public class ResultAnalysisServiceImpl implements ResultAnalysisService {

    private final ScrapeNewsPageServiceImpl scrapePageService;
    private final WatsonNLUService watsonNLUService;
    private final StorageResultRepository storageResultRepository;

    public ResultAnalysisServiceImpl(ScrapeNewsPageServiceImpl scrapePageService,
                                     WatsonNLUService watsonNLUService,
                                     StorageResultRepository storageResultRepository) {
        this.scrapePageService = scrapePageService;
        this.watsonNLUService = watsonNLUService;
        this.storageResultRepository = storageResultRepository;
    }

    private long initTime() {
        return System.currentTimeMillis();
    }

    private long endTime(long startTime) {
        long time = System.currentTimeMillis() - startTime;
        return time;
    }

    private void saveResult(StorageResult result) {
        storageResultRepository.save(result);
    }

    @Override
    public StorageResult analysis(NewsResultPage newsResultPage) {
        StorageResult storageResult = new StorageResult();
        return analyseAndStorageResult(storageResult, newsResultPage);

    }

    private StorageResult analyseAndStorageResult(StorageResult storageResult, NewsResultPage newsResultPage) {
        long time = initTime();

        try {
            String jsonAnalyseResult = scrapeAndAnalysisContent(newsResultPage);
            storageResult.setContentAnaliseResult(WatsonAnaliseResult.fromJson(jsonAnalyseResult));
            storageResult.setStatus(StorageStatus.SUCCESS,"OK");
            saveResult(storageResult);
        } catch (PageValidatorException e) {
            saveNotFoundResult(storageResult, e.getMessage());
        } catch (ScrapingException e) {
            saveInvalidQueryResult(storageResult, e.getMessage());
        } finally {
            storageResult.setAnalysisTime(endTime(time));
            return storageResult;
        }

    }

    private String  scrapeAndAnalysisContent(NewsResultPage newsResultPage) throws PageValidatorException, ScrapingException, NLUException {
        PageBody pageBody = scrapePageService.scrapeNewsPages(newsResultPage);

        String jsonResponse = watsonNLUService.startAnalysis(pageBody.getTextContent());
        return jsonResponse;
    }

    private void saveNotFoundResult(StorageResult result, String message) {
        result.setStatus(StorageStatus.NOT_FOUND, message);
        saveResult(result);
    }

    private void saveInvalidQueryResult(StorageResult result, String message) {
        result.setStatus(StorageStatus.INVALID_QUERY, message);
        saveResult(result);
    }
}
