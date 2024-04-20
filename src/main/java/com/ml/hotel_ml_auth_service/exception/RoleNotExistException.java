package com.ml.hotel_ml_auth_service.exception;

public class RoleNotExistException extends RuntimeException {
    public RoleNotExistException() {
        super("Cannot find role");
    }
}
