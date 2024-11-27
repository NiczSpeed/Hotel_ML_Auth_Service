package com.ml.hotel_ml_auth_service.exception;

public class ErrorWhileSaveRolesException extends RuntimeException {
    public ErrorWhileSaveRolesException() {
        super("Error while creating roles!");
    }
}
