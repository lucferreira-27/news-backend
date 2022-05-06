package com.example.newsbackend.service.impl.nlu.watson;

import com.example.newsbackend.service.nlu.AnalyzeService;
import com.ibm.watson.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.natural_language_understanding.v1.model.*;
import org.springframework.stereotype.Service;

@Service
public class WatsonAnalyze implements AnalyzeService<WatsonAnalyzeOptions> {

    private  NaturalLanguageUnderstanding nluService;

    public WatsonAnalyze(NaturalLanguageUnderstanding nluService)  {
        this.nluService = nluService;

    }

    public AnalysisResults analyze(WatsonAnalyzeOptions customOptions) {
        AnalyzeOptions options = getAnalyzeOptions(customOptions.getText(),customOptions);
        AnalysisResults response = nluService.analyze(options).execute().getResult();
        return response;
    }
    private AnalyzeOptions getAnalyzeOptions(String text,WatsonAnalyzeOptions customOptions) {
        AnalyzeOptions options = new AnalyzeOptions.Builder()
                .text(text)
                .features(new Features.Builder()
                        .sentiment(new SentimentOptions.Builder()
                                .build())
                        .entities(new EntitiesOptions.Builder()
                                .emotion(customOptions.isEntitiesEmotion())
                                .sentiment(customOptions.isEntitiesSentiment())
                                .limit(customOptions.getEntitiesLimit())
                                .build())
                        .concepts(new ConceptsOptions.Builder()
                                .limit(customOptions.getConceptsLimit())
                                .build())
                        .keywords(new KeywordsOptions.Builder()
                                .emotion(customOptions.isKeywordsEmotion())
                                .sentiment(customOptions.isKeywordsSentiment())
                                .limit(customOptions.getKeywordsLimit())
                                .build())
                        .build())
                .build();
        return options;
    }
}
