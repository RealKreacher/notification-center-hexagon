package com.wandera.hw.notificationcenter.user.infrastructure.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@Builder
public record ErrorResponseBody(long timestamp,
                                int status,
                                String error,
                                String message,
                                String path
                               ) {

    public static ErrorResponseBody of(WebRequest request, HttpStatus status, String message) {
        var requestURI = ((ServletWebRequest) request).getRequest().getRequestURI();

        return ErrorResponseBody.builder()
                .timestamp(System.currentTimeMillis())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(requestURI)
                .build();
    }
}
