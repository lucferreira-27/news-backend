package com.example.newsbackend.service.scrape.dynamic;

import com.example.newsbackend.service.scrape.ScrapingStrategy;
import com.example.newsbackend.service.scrape.stable.HtmlParser;
import org.springframework.stereotype.Service;

@Service("DYNAMIC")
public class DynamicScrapingBot extends ScrapingStrategy  {
    private final FirefoxSeleniumBrowser emuBrowser;

    public DynamicScrapingBot(HtmlParser htmlParser, FirefoxSeleniumBrowser emuBrowser) {
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
