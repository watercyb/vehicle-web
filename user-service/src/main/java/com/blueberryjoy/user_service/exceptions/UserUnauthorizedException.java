package com.blueberryjoy.user_service.exceptions;

public class UserUnauthorizedException extends RuntimeException {
    public UserUnauthorizedException() {
        super("User is Unauthorized.");
    }
}
