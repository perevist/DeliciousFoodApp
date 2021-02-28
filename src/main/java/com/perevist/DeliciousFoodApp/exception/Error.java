package com.perevist.DeliciousFoodApp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Error {

    MAIL_CAN_NOT_BE_SENT("Mail can not be sent to the user"),
    VERIFICATION_TOKEN_IS_NOT_VALID("Verification token is not valid"),
    USERNAME_ALREADY_EXISTS("Username already exists"),
    EMAIL_ALREADY_EXISTS("Email already exists"),
    USER_DOES_NOT_EXIST("User does not exist"),
    CATEGORY_DOES_NOT_EXIST("Recipe category does not exist"),
    RECIPE_DOES_NOT_EXIST("Recipe does not exist"),
    USER_CAN_NOT_DO_THIS_OPERATION("User can not do this operation"),
    COMMENT_DOES_NOT_EXIST("Comment does not exist"),
    COMMENT_DOES_NOT_BELONG_TO_THIS_RECIPE("Comment does not belong to this recipe");

    private String message;
}
