package com.example.newsbackend.service.impl.scrape.dynamic;

import com.example.newsbackend.exception.EmuBrowserException;
import com.example.newsbackend.service.impl.scrape.ScrapingStrategyAbstract;
import com.example.newsbackend.service.impl.scrape.stable.HTMLParserImpl;
import org.springframework.stereotype.Service;

@Service("DYNAMIC")
public class DynamicScrapingBot extends ScrapingStrategyAbstract {
    private final FirefoxImplSeleniumBrowser emuBrowser;

    public DynamicScrapingBot(HTMLParserImpl htmlParser, FirefoxImplSeleniumBrowser emuBrowser) {
        super(htmlParser);
        this.emuBrowser = emuBrowser;
    }

    @Override
    protected String scrapePage(String url) throws EmuBrowserException {
        emuBrowser.navigate(url);
        String retrievedContent = emuBrowser.retrieveContent();
        return retrievedContent;
    }

}
