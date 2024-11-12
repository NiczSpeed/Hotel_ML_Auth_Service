package com.ml.hotel_ml_auth_service.exception;

public class ErrorWhileLogin extends RuntimeException {
    public ErrorWhileLogin() {
        super("Error While Login!");
    }
}
