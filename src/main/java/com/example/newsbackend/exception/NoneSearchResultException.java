package com.example.newsbackend.service.serp;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NoneSearchResultException extends ScaleAPIException{

    public NoneSearchResultException(String message) {
        super(message);
    }

    public NoneSearchResultException(Exception exception) {
        super(exception);
    }

    public NoneSearchResultException(String message, Exception exception) {
        super(message, exception);
    }
}
