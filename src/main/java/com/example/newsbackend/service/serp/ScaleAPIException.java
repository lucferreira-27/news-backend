package com.example.newsbackend.service.serp;

import com.example.newsbackend.service.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

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
