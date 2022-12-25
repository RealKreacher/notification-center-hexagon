package com.wandera.hw.notificationcenter.user.infrastructure;


import com.wandera.hw.notificationcenter.user.infrastructure.exception.ErrorResponseBody;
import com.wandera.hw.notificationcenter.user.infrastructure.exception.NoSuchNotificationException;
import com.wandera.hw.notificationcenter.user.infrastructure.exception.NoSuchUserException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class UserNotificationAdvice extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = NoSuchUserException.class)
    public ResponseEntity<Object> handleException(NoSuchUserException exception, WebRequest request) {
        var status = HttpStatus.BAD_REQUEST;
        var responseBody = ErrorResponseBody.of(request, status, exception.getMessage());

        return handleExceptionInternal(exception,
                responseBody,
                new HttpHeaders(),
                status,
                request
        );
    }

    @ResponseBody
    @ExceptionHandler(value = NoSuchNotificationException.class)
    public ResponseEntity<Object> handleException(NoSuchNotificationException exception, WebRequest request) {
        var status = HttpStatus.NOT_FOUND;
        var responseBody = ErrorResponseBody.of(request, status, exception.getMessage());

        return handleExceptionInternal(exception,
                responseBody,
                new HttpHeaders(),
                status,
                request
        );
    }
}
