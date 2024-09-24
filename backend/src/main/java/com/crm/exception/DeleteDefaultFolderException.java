package com.crm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class DeleteDefaultFolderException extends RuntimeException{
    public DeleteDefaultFolderException(String message) {
        super(message);
    }
}
