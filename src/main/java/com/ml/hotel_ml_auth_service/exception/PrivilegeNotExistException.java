package com.ml.hotel_ml_auth_service.exception;

public class PrivilegeNotExistException extends RuntimeException {
    public PrivilegeNotExistException() {
        super("Cannot find privilege!");
    }
}
