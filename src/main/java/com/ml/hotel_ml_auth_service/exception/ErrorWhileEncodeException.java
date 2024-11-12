package com.ml.hotel_ml_auth_service.exception;

public class ErrorWhileEncodeException extends RuntimeException {
    public ErrorWhileEncodeException() {
        super("Error while encoding!");
    }
}
