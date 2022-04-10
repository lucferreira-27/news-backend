package com.example.newsbackend.service.scrape.stable;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Service
public class PureContentExtract {

    private final DownloadPage downloadPage;
    private final TextExtractor textExtractor;

    public PureContentExtract(DownloadPage downloadPage, TextExtractor textExtractor) {
        this.downloadPage = downloadPage;
        this.textExtractor = textExtractor;
    }

    public String extract(String url) throws IOException {
        String encodeUrl = encodeUrl(url);
        InputStream inputStream = downloadPage.getPageInputStream(new URL(encodeUrl));
        return textExtractor.extractTextFromInputStream(inputStream);
    }
    private String encodeUrl(String url)  {
        return url.replace(" ", "%20");
    }
}
