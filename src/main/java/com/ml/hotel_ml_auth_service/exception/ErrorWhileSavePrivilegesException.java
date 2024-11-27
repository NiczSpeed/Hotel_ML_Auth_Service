package com.ml.hotel_ml_auth_service.exception;

public class ErrorWhileSavePrivilegesException extends RuntimeException {
    public ErrorWhileSavePrivilegesException() {
        super("Error while save privileges!");
    }
}
