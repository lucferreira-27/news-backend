package com.example.newsbackend.service.nlu;

import com.example.newsbackend.service.nlu.watson.WatsonAnalyseResult;
import com.ibm.watson.natural_language_understanding.v1.model.AnalysisResults;

public interface NaturalLanguageUnderstandingService {
    public String startAnalysis(String text) throws NLUException;
}
