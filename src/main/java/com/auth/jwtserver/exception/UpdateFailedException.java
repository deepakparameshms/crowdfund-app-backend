package com.auth.jwtserver.exception;

public class UpdateFailedException extends RuntimeException {
    
    public UpdateFailedException(String message, Throwable cause){
        super(message, cause);
    }
}
