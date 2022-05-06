package com.example.newsbackend.service.impl.scrape.stable;

import com.example.newsbackend.service.scrape.ContentExtractService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Service
public class ContentExtractImpl implements ContentExtractService {

    private final DownloadPageImpl downloadPageImpl;
    private final TextExtractorImpl textExtractorImpl;

    public ContentExtractImpl(DownloadPageImpl downloadPageImpl, TextExtractorImpl textExtractorImpl) {
        this.downloadPageImpl = downloadPageImpl;
        this.textExtractorImpl = textExtractorImpl;
    }

    public String extract(String url) throws IOException {
        String encodeUrl = encodeUrl(url);
        InputStream inputStream = downloadPageImpl.getPageInputStream(new URL(encodeUrl));
        return textExtractorImpl.extractTextFromInputStream(inputStream);
    }
    private String encodeUrl(String url)  {
        return url.replace(" ", "%20");
    }
}
