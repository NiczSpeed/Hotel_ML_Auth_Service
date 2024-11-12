package com.ml.hotel_ml_auth_service.exception;

public class ErrorWhileSaveRoles extends RuntimeException {
    public ErrorWhileSaveRoles() {
        super("Error while creating roles!");
    }
}
