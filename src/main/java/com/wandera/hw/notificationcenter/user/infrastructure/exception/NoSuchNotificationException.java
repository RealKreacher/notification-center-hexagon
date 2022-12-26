package com.wandera.hw.notificationcenter.user.infrastructure.exception;

public class NoSuchNotificationException extends RuntimeException {

    public static final String NOTIFICATION_NOT_FOUND = "Notification not found";

    public NoSuchNotificationException(String errorMessage) {
        super(errorMessage);
    }
}

