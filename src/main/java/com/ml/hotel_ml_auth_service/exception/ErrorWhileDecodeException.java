package com.ml.hotel_ml_auth_service.exception;

public class ErrorWhileDecodeException extends RuntimeException {
    public ErrorWhileDecodeException() {
        super("Error While Decoding!");
    }
}