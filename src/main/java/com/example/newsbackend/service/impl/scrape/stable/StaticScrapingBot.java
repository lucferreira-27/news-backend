package com.example.newsbackend.service.impl.scrape.stable;

import com.example.newsbackend.service.impl.scrape.ScrapingStrategyAbstract;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service("STATIC")
public class StaticScrapingBot  extends ScrapingStrategyAbstract {

    private final ContentExtractImpl contentExtractImpl;

    public StaticScrapingBot(HTMLParserImpl htmlParser, ContentExtractImpl contentExtractImpl) {
        super(htmlParser);
        this.contentExtractImpl = contentExtractImpl;
    }

    protected String scrapePage(String url) throws IOException {

        String contentExtract = this.contentExtractImpl.extract(url);
        return contentExtract;
    }


}
