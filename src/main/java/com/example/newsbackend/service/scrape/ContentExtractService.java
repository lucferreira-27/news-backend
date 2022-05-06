package com.example.newsbackend.service.scrape;

import java.io.IOException;

public interface ContentExtractService {
    public String extract(String url) throws IOException;
}
