package com.wandera.hw.notificationcenter.user.infrastructure.exception;

public class NoSuchNotificationException extends RuntimeException {

    public NoSuchNotificationException(String errorMessage) {
        super(errorMessage);
    }
}

