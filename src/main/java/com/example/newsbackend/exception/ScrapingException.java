package com.example.newsbackend.exception;

public class ScrapingException extends Exception {
    public ScrapingException(String message) {
        super(message);
    }
    public ScrapingException(Exception exception) {
        super(exception);
    }
    public ScrapingException(String message,Exception exception) {
        super(message,exception);
    }
}
