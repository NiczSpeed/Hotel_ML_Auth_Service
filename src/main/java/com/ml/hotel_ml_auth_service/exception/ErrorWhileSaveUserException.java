package com.ml.hotel_ml_auth_service.exception;

public class ErrorWhileSaveUserException extends RuntimeException {
    public ErrorWhileSaveUserException() {
        super("Error while saving user!");
    }
}
