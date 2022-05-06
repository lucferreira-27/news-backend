package com.example.newsbackend.service.nlu;

import com.example.newsbackend.exception.NLUException;

public interface NaturalLanguageUnderstandingService {
    public String startAnalysis(String text) throws NLUException;
}
