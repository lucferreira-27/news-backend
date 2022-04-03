package com.example.newsbackend.service.scrape.dynamic;

public class EmuBrowserException extends RuntimeException {

    public EmuBrowserException(String message) {
        super(message);
    }
    public EmuBrowserException(Exception exception) {
        super(exception);
    }
    public EmuBrowserException(String message,Exception exception) {
        super(message,exception);
    }

}
