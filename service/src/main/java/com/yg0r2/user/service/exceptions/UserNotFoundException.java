package com.yg0r2.user.service.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }

}
