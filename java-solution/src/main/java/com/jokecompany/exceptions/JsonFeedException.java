package com.jokecompany.exceptions;

public class JsonFeedException extends Exception{

    public JsonFeedException() {
        super();
    }

    public JsonFeedException(String message) {
        super(message);
    }

    public JsonFeedException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonFeedException(Throwable cause) {
        super(cause);
    }

    public JsonFeedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
