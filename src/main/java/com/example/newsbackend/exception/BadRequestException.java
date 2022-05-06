package com.example.newsbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Bad Request")
public class BadRequestException extends RuntimeException{
    public BadRequestException(String msg){
        super(msg);
    }
    public BadRequestException(String msg, Exception e){
        super(msg, e);
    }
    public BadRequestException(Exception e){
        super(e);
    }
}
