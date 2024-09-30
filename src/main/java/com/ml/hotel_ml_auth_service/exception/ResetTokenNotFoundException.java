package com.ml.hotel_ml_auth_service.exception;

public class ResetTokenNotFoundException extends RuntimeException{
    public ResetTokenNotFoundException() {super("Cannot find Token!");}
}

