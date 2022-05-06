package com.example.newsbackend.service.impl.serp;

import com.example.newsbackend.service.impl.scrape.stable.TextExtractorImpl;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

@Service
public class HTTPRequestServiceImpl {

    private final TextExtractorImpl textExtractorImpl;

    public HTTPRequestServiceImpl(TextExtractorImpl textExtractorImpl) {
        this.textExtractorImpl = textExtractorImpl;
    }

    public String sendRequest(URL url) throws IOException {
        URLConnection conn = url.openConnection();
        return textExtractorImpl.extractTextFromInputStream(conn.getInputStream());
    }


}
