package com.ml.hotel_ml_auth_service.exception;

public class ErrorWhileLoginException extends RuntimeException {
    public ErrorWhileLoginException() {
        super("Error While Login!");
    }
}
