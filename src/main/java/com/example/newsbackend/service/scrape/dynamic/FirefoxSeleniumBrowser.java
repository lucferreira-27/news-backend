package com.example.newsbackend.service.scrape.dynamic;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Service;

@Service
public class FirefoxSeleniumBrowser extends SeleniumBrowser {
    public static final String DRIVER_NAME = "geckodriver";
    public static final String DRIVER_PROPERTY = "webdriver.gecko.driver";
    public static final Boolean IS_HEADLESS = true;

    @Override
    protected RemoteWebDriver initRemoteWebDriver(String binaryPath, String driverPath) {
        System.setProperty(DRIVER_PROPERTY, driverPath);
        FirefoxOptions options = new FirefoxOptions();
        options.setBinary(binaryPath);
        options.setHeadless(IS_HEADLESS);
        return createFirefoxDriver(options);
    }
    protected FirefoxDriver createFirefoxDriver(FirefoxOptions options) {
        return new FirefoxDriver(options);
    }


}
