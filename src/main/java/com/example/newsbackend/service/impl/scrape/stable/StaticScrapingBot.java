package com.example.newsbackend.service.scrape.stable;

import com.example.newsbackend.service.scrape.ScrapingStrategy;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service("STATIC")
public class StaticScrapingBot  extends ScrapingStrategy {

    private final PureContentExtract pureContentExtract;

    public StaticScrapingBot(HtmlParser htmlParser,PureContentExtract pureContentExtract) {
        super(htmlParser);
        this.pureContentExtract = pureContentExtract;
    }

    protected String scrapePage(String url) throws IOException {

        String contentExtract = pureContentExtract.extract(url);
        return contentExtract;
    }


}
