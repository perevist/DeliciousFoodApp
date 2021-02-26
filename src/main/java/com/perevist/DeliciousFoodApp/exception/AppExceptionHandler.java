package com.perevist.DeliciousFoodApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(value = DeliciousFoodAppException.class)
    public ResponseEntity<ErrorInfo> handleException(DeliciousFoodAppException e) {

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        Error error = e.getError();

        switch (error) {
            case MAIL_CAN_NOT_BE_SENT:
                httpStatus = HttpStatus.CONFLICT;
                break;
        }

        return ResponseEntity.status(httpStatus)
                .body(new ErrorInfo(e.getError().getMessage()));
    }
}
