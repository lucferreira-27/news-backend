package com.example.newsbackend.exception;

public class NLUException extends Exception {

    public NLUException(String message) {
        super(message);
    }
    public NLUException(String message, Throwable cause) {
        super(message, cause);
    }
}
