package com.wandera.hw.notificationcenter.user.infrastructure.exception;

public class CSVParseException extends RuntimeException {
    public CSVParseException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
