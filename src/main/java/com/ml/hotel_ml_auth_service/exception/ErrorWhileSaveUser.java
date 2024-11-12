package com.ml.hotel_ml_auth_service.exception;

public class ErrorWhileSaveUser extends RuntimeException {
    public ErrorWhileSaveUser() {
        super("Error while saving user!");
    }
}
