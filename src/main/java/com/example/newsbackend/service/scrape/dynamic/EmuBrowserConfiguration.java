package com.example.newsbackend.service.scrape.dynamic;

public class EmuBrowserConfiguration {
    private String browserPath;
    private String browserName;
    private String browserVersion;
    private String browserDriver;

    public EmuBrowserConfiguration(String browserPath) {
        this.browserPath = browserPath;
    }

    public EmuBrowserConfiguration(String browserPath,
                                   String browserDriver) {
        this.browserPath = browserPath;
        this.browserDriver = browserDriver;
    }

    public EmuBrowserConfiguration(String browserPath,
                                   String browserName,
                                   String browserVersion,
                                   String browserDriver) {
        this.browserPath = browserPath;
        this.browserName = browserName;
        this.browserVersion = browserVersion;
        this.browserDriver = browserDriver;
    }

    public String getBrowserName() {
        return browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    public String getBrowserPath() {
        return browserPath;
    }

    public void setBrowserPath(String browserPath) {
        this.browserPath = browserPath;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }

    public String getBrowserDriver() {
        return browserDriver;
    }

    public void setBrowserDriver(String browserDriver) {
        this.browserDriver = browserDriver;
    }
}
