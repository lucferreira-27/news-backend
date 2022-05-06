package com.example.newsbackend.service.impl.scrape.dynamic;

import com.example.newsbackend.service.scrape.EmuBrowserService;
import org.openqa.selenium.remote.RemoteWebDriver;


public abstract class SeleniumBrowserAbstract implements EmuBrowserService {
    private RemoteWebDriver driver;
    private EmuBrowserConfiguration config;
    @Override
    public void init(EmuBrowserConfiguration configuration) {
        driver = initRemoteWebDriver(
                configuration.getBrowserPath(),
                configuration.getBrowserDriver());
        this.config = configuration;
    }
    @Override
    public void reinit(){
        if(!isInitialized()){
            throw new IllegalStateException("Browser need to be initialized at least once before reinitialization");
        }
        init(config);
    }

    protected abstract RemoteWebDriver initRemoteWebDriver(String binaryPath, String driverPath);

    @Override
    public boolean isInitialized() {
        boolean isInitialized = !driver.toString().contains("(null)");
        return isInitialized;
    }

    @Override
    public void navigate(String url) {
        if(!isInitialized()){
            throw new IllegalStateException("Browser is not initialized");
        }
        driver.navigate().to(url);
    }

    @Override
    public String retrieveContent() {
        if(!isInitialized()){
            throw new IllegalStateException("Browser is not initialized");
        }
        return driver.getPageSource();
    }

    @Override
    public void close() {
        driver.quit();
    }

    @Override
    public void destroy() {
        driver.close();
    }

}
