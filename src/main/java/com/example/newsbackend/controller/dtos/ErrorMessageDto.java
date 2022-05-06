package com.example.newsbackend.controller.dtos;

import java.util.Date;

public class ErrorMessageDto {
    private int statusCode;
    private Date timestamp;
    private String message;
    private String path;


    public ErrorMessageDto(int statusCode, Date timestamp, String message, String path) {
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.message = message;
        this.path = path;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
