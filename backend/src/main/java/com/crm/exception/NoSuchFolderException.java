package com.crm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchFolderException extends RuntimeException{

    public NoSuchFolderException(String message) {
        super(message);
    }
}
