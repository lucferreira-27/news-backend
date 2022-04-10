package com.example.newsbackend.service.serp;

public class ScaleAPIException extends RuntimeException {
    public ScaleAPIException(String message) {
        super(message);
    }
    public ScaleAPIException(Exception exception) {
        super(exception);
    }
    public ScaleAPIException(String message,Exception exception) {
        super(message,exception);
    }
}
