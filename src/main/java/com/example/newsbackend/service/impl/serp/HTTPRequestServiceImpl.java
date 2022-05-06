package com.example.newsbackend.service.impl.serp;

import com.example.newsbackend.service.impl.scrape.stable.TextExtractor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

@Service
public class HTTPRequest {

    private final TextExtractor textExtractor;

    public HTTPRequest(TextExtractor textExtractor) {
        this.textExtractor = textExtractor;
    }

    public String sendRequest(URL url) throws IOException {
        URLConnection conn = url.openConnection();
        return textExtractor.extractTextFromInputStream(conn.getInputStream());
    }


}
