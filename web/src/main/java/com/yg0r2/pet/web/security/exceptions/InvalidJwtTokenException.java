package com.yg0r2.pet.web.security.exceptions;

import org.springframework.security.core.AuthenticationException;

public class InvalidJwtTokenException extends AuthenticationException {

    public InvalidJwtTokenException(String message) {
        super(message);
    }

    public InvalidJwtTokenException(String message, Throwable cause) {
        super(message, cause);
    }

}
