package com.blueberryjoy.gateway.exceptions;

public class UserUnauthorizedException extends RuntimeException {
    public UserUnauthorizedException() {
        super("User is Unauthorized.");
    }
}
