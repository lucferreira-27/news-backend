package com.example.newsbackend.exception;

import com.example.newsbackend.exception.BadRequestException;

public class ScaleAPIException extends BadRequestException {
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
