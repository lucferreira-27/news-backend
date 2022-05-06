package com.example.newsbackend.service.scrape;

import java.io.IOException;
import java.io.InputStream;

public interface TextExtractorService {
    public String extractTextFromInputStream(InputStream inputStream) throws IOException;
}
