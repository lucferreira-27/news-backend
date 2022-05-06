package com.example.newsbackend.service.impl.scrape.dynamic;

import org.springframework.stereotype.Service;

@Service
public interface EmuBrowser {

    public void init(EmuBrowserConfiguration configuration);
    public void reinit();
    public boolean isInitialized();
    public void navigate(String url);
    public String retrieveContent();
    public void close();
    public void destroy();
}
