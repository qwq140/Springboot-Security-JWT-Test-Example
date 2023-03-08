package com.pjay.securityjwt.handler.ex;

public class CustomForbiddenException extends RuntimeException{

    public CustomForbiddenException(String message) {
        super(message);
    }
}
