package com.yg0r2.fuse.exception;

public class FuseException extends RuntimeException {

    public FuseException(Throwable cause) {
        super(cause);
    }

    public FuseException(String message, Throwable cause) {
        super(message, cause);
    }

}
