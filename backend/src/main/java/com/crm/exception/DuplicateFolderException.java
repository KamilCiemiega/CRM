package com.crm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateFolderException extends RuntimeException {
    public DuplicateFolderException(String message) {
        super(message);
    }

    public DuplicateFolderException(String message, Throwable cause) {
        super(message, cause);
    }
}