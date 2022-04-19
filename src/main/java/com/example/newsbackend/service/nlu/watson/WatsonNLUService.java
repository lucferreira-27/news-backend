package com.example.newsbackend.service.nlu.watson;

import com.example.newsbackend.service.nlu.NaturalLanguageUnderstandingService;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.natural_language_understanding.v1.model.AnalysisResults;
import org.springframework.stereotype.Service;

@Service
public class WatsonNLUService implements NaturalLanguageUnderstandingService {
    @Override
    public String startAnalysis(String text) {

        NaturalLanguageUnderstanding nlu = getNaturalLanguageUnderstanding();
        AnalysisResults response =  nlu.analyze(null).execute().getResult();
        String result = response.toString();

        return null;
    }

    private NaturalLanguageUnderstanding getNaturalLanguageUnderstanding(){
        IamAuthenticator authenticator = new IamAuthenticator
                .Builder()
                .apikey("{apikey}")
                .build();
        NaturalLanguageUnderstanding naturalLanguageUnderstanding = new NaturalLanguageUnderstanding("2022-04-07", authenticator);
        return naturalLanguageUnderstanding;
    }
}
