package com.example.newsbackend.service.nlu;
import com.ibm.watson.natural_language_understanding.v1.model.AnalysisResults;

public interface AnalyzeService<T extends AnalyzeOptions> {
    public AnalysisResults analyze(T customOptions);
}
