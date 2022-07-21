package com.esprit.voyage.services.exception;

public class CBException extends RuntimeException {
    public CBException(String message) {
        super(message);
    }

    public CBException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public CBException(Throwable throwable) {
        super(throwable);
    }
}
