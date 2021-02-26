package com.perevist.DeliciousFoodApp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Error {

    MAIL_CAN_NOT_BE_SENT("Mail can not be sent to the user"),
    VERIFICATION_TOKEN_IS_NOT_VALID("Verification token is not valid");

    private String message;
}
