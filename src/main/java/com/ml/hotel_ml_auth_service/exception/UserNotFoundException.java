package com.ml.hotel_ml_auth_service.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(){super("Cannot find user");}
}
