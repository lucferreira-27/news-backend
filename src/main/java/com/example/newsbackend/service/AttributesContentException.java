package com.example.newsbackend.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public class AttributesContentException extends RuntimeException {
    public AttributesContentException(String message) {
        super(message);
    }

    public AttributesContentException(String message, JsonProcessingException e) {
        super(message,e);
    }
}
