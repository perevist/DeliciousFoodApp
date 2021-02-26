package com.perevist.DeliciousFoodApp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeliciousFoodAppException extends RuntimeException {
    private Error error;
}

