package com.example.newsbackend.service.nlu;

public class NLUException extends Exception {

    public NLUException(String message) {
        super(message);
    }
    public NLUException(String message, Throwable cause) {
        super(message, cause);
    }
}
