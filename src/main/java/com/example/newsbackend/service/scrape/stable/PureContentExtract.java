package com.example.newsbackend.service.scrape.stable;

import com.example.newsbackend.service.scrape.stable.DownloadPage;
import com.example.newsbackend.service.scrape.stable.HtmlExtractor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Service
public class PureContentExtract {

    private final DownloadPage downloadPage;
    private final HtmlExtractor htmlExtractor;

    public PureContentExtract(DownloadPage downloadPage, HtmlExtractor htmlExtractor) {
        this.downloadPage = downloadPage;
        this.htmlExtractor = htmlExtractor;
    }

    public String extract(String url) throws IOException {
        InputStream inputStream = downloadPage.getPageInputStream(new URL(url));
        return htmlExtractor.extractHtmlFromInputStream(inputStream);
    }
}
