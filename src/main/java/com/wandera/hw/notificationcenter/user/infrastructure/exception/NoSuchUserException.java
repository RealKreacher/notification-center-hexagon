package com.wandera.hw.notificationcenter.user.infrastructure.exception;

public class NoSuchUserException extends RuntimeException {

    public NoSuchUserException(String errorMessage) {
        super(errorMessage);
    }
}

