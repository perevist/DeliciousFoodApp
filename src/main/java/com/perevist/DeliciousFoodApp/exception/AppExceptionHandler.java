package com.perevist.DeliciousFoodApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(value = DeliciousFoodAppException.class)
    public ResponseEntity<ErrorInfo> handleException(DeliciousFoodAppException e) {

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        Error error = e.getError();

        switch (error) {
            case MAIL_CAN_NOT_BE_SENT:
            case USERNAME_ALREADY_EXISTS:
            case EMAIL_ALREADY_EXISTS:
                httpStatus = HttpStatus.CONFLICT;
                break;
            case VERIFICATION_TOKEN_IS_NOT_VALID:
            case USER_DOES_NOT_EXIST:
            case CATEGORY_DOES_NOT_EXIST:
            case RECIPE_DOES_NOT_EXIST:
            case USER_CAN_NOT_DO_THIS_OPERATION:
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
        }

        return ResponseEntity.status(httpStatus)
                .body(new ErrorInfo(e.getError().getMessage()));
    }
}
