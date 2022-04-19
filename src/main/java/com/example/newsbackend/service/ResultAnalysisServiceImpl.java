package com.example.newsbackend.service;

import com.example.newsbackend.repository.history.ContentAnaliseResult;
import com.example.newsbackend.repository.history.StorageResult;
import com.example.newsbackend.repository.page.PageBody;
import com.example.newsbackend.service.nlu.watson.WatsonNLUService;
import com.example.newsbackend.service.scrape.ScrapingException;
import com.example.newsbackend.service.serp.NewsResultPage;
import org.springframework.stereotype.Service;

@Service
public class ResultAnalysisServiceImpl implements ResultAnalysisService {

    private final ScrapeNewsPageServiceImpl scrapePageService;
    private final WatsonNLUService watsonNLUService;
    public ResultAnalysisServiceImpl(ScrapeNewsPageServiceImpl scrapePageService, WatsonNLUService watsonNLUService) {
        this.scrapePageService = scrapePageService;
        this.watsonNLUService = watsonNLUService;
    }


    @Override
    public StorageResult analysis(NewsResultPage newsResultPage) {
        StorageResult storageResult = new StorageResult();
        try {
          PageBody pageBody = scrapePageService.scrapeNewsPages(newsResultPage);
          watsonNLUService.startAnalysis(pageBody.getTextContent());

        } catch (PageValidatorException e) {

        } catch (ScrapingException e) {

        } finally {
            return storageResult;
        }
    }
    private ContentAnaliseResult analiseContent(PageBody pageBody) {
        return null;
    }
}
