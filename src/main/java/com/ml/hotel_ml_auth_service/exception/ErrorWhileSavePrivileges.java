package com.ml.hotel_ml_auth_service.exception;

public class ErrorWhileSavePrivileges extends RuntimeException {
    public ErrorWhileSavePrivileges() {
        super("Error while save privileges!");
    }
}
