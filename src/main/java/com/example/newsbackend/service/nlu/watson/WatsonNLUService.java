package com.example.newsbackend.service.nlu.watson;

import com.example.newsbackend.service.nlu.NLUException;
import com.example.newsbackend.service.nlu.NaturalLanguageUnderstandingService;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.natural_language_understanding.v1.model.AnalysisResults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WatsonNLUService implements NaturalLanguageUnderstandingService {

    private final WatsonAnalyze watsonAnalyze;

    public WatsonNLUService(WatsonAnalyze watsonAnalyze) {
        this.watsonAnalyze = watsonAnalyze;
    }

    @Override
    public String startAnalysis(String text) throws NLUException {
        WatsonAnalyzeOptions options = buildOptions(text);
        return analyze(options);
    }

    private WatsonAnalyzeOptions buildOptions(String text) {
        WatsonAnalyzeOptions options = new WatsonAnalyzeOptions.Builder()
                .text(text)
                .conceptsLimit(3)   // limit the number of concepts returned
                .conceptsEmotion(true) // include emotion
                .conceptsSentiment(true) // include sentiment
                .entitiesEmotion(true) // include emotion
                .entitiesSentiment(true) // include sentiment
                .entitiesLimit(3) // limit the number of entities returned
                .keywordsEmotion(true) // include emotion
                .keywordsSentiment(true) // include sentiment
                .keywordsLimit(3) // limit the number of keywords returned
                .build();
        return options;
    }

    private String analyze(WatsonAnalyzeOptions options) throws NLUException {
        try {
            AnalysisResults response = watsonAnalyze.analyze(options);
            String result = response.toString();
            return result; // return the JSON response
        } catch (Exception e) {
            throw new NLUException("Failed to analyze text", e);
        }
    }
}



